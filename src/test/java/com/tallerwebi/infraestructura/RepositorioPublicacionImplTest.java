package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.implementacion.interfaces.RepositorioCliente;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Publicacion;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})

public class RepositorioPublicacionImplTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPublicacion repositorioPublicacion;
    private RepositorioCliente repositorioCliente;


    @BeforeEach
    public void init(){
        this.repositorioPublicacion = new RepositorioPublicacionImpl(sessionFactory);
        this.repositorioCliente = new RepositorioClienteImpl(sessionFactory);

    }

    @Test
    @Transactional
    @Rollback
    public void clienteCreaUnaPublicacionEnLaBaseDeDatosSiFueCreadaMeDevuelveUnTrue(){
        Cliente cliente = crearCliente();
        repositorioCliente.guardar(cliente);

        Publicacion publicacion = crearPublicacion(cliente);
        repositorioPublicacion.guardar(publicacion);

        String hql = "FROM Publicacion p WHERE p.clientePublicacion.id = :clienteId AND p.nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("clienteId", cliente.getId());
        query.setParameter("nombre", publicacion.getNombre());

        Publicacion publicacionObtenida = (Publicacion) query.getSingleResult();

        assertThat(publicacionObtenida, notNullValue());
        assertThat(publicacionObtenida.getNombre(), equalTo(publicacion.getNombre()));
        assertThat(publicacionObtenida.getClientePublicacion().getId(), equalTo(cliente.getId()));
    }


    @Test
    @Transactional
    @Rollback
    public void clienteCreaUnaPublicacionEnLaBaseDeDatosYLuegoLaEliminaSiLaPublicacionFueELiminadaDevuelveUnTrue(){
        Cliente cliente = crearCliente();
        repositorioCliente.guardar(cliente);

        Publicacion publicacion = crearPublicacion(cliente);
        repositorioPublicacion.guardar(publicacion);
        repositorioPublicacion.eliminar(publicacion);

        // Verificar que la publicación ya no existe en la base de datos
        String hql = "FROM Publicacion p WHERE p.id = :publicacionId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("publicacionId", publicacion.getId());

        List<Publicacion> publicacionesObtenidas = query.getResultList();

        // Aserción para verificar que la publicación fue eliminada
        assertThat(publicacionesObtenidas, is(empty()));
    }

    @Test
    @Transactional
    @Rollback
    public void clienteSubeUnaPublicacionALaBaseDeDatosYLuegoModificaElPrecioYSeGuardaEnLaBaseDeDatosElNuevoPrecio(){
        Cliente cliente = crearCliente();
        repositorioCliente.guardar(cliente);

        Publicacion publicacion = crearPublicacion(cliente);
        repositorioPublicacion.guardar(publicacion);

        Double nuevoPrecio = 10.000;
        publicacion.setPrecio(nuevoPrecio);
        repositorioPublicacion.guardar(publicacion);

        String hql = "FROM Publicacion p WHERE p.id = :publicacionId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("publicacionId", publicacion.getId());

        Publicacion publicacionActualizada = (Publicacion) query.getSingleResult();

        // 5. Verificar que el precio se ha actualizado correctamente
        assertThat(publicacionActualizada.getPrecio(), equalTo(nuevoPrecio));
    }


    @Test
    @Transactional
    @Rollback
    public void dadoQueUnClienteSubeUnaPublicacionALaBaseDeDatosYLuegoQuierePausarEsaPublicacionDevuelveTrue(){
        Cliente cliente = crearCliente();
        repositorioCliente.guardar(cliente);

        Publicacion publicacion = crearPublicacion(cliente);
        repositorioPublicacion.guardar(publicacion);

        publicacion.setPublicacionPausada(Boolean.TRUE);
        repositorioPublicacion.guardar(publicacion);

        String hql = "FROM Publicacion p WHERE p.id = :publicacionId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("publicacionId", publicacion.getId());

        Publicacion publicacionPausada = (Publicacion) query.getSingleResult();

        // 5. Verificar que la publicación está pausada
        assertThat(publicacionPausada.getPublicacionPausada(), is(true));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueUnClienteCreaVariasPublicacionesEntoncesPuedoListarlasTodas(){
        Cliente cliente = crearCliente();
        repositorioCliente.guardar(cliente);

        Publicacion publicacion = crearPublicacion(cliente);
        repositorioPublicacion.guardar(publicacion);

        Publicacion publicacion2 = crearPublicacionNueva(cliente);
        repositorioPublicacion.guardar(publicacion2);

        List<Publicacion> publicacionesObtenidaDelCliente = repositorioPublicacion.listarPublicacionPorCliente(cliente);

        assertThat(publicacionesObtenidaDelCliente, hasSize(2)); // Verifica que hay 2 publicaciones
        assertThat(publicacionesObtenidaDelCliente.stream().map(Publicacion::getNombre).collect(Collectors.toList()),
                containsInAnyOrder("Cemento", "Ladrillo"));
    }





    // Metodos
    private Cliente crearCliente(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Lionel Andres");
        cliente.setApellido("Messi");
        cliente.setEmail("LeoMessiCampeonDelMundoQatar2022");
        cliente.setTelefono("18122022");
        cliente.setPassword("1234");
        return cliente;
    }

    private Publicacion crearPublicacion(Cliente cliente){
        Publicacion publicacion = new Publicacion();
        publicacion.setNombre("Cemento");
        publicacion.setStock(4);
        publicacion.setPrecio(15.000);
        publicacion.setClientePublicacion(cliente);
        publicacion.setFechaInicioPublicacion(LocalDate.now());
        publicacion.setPublicacionPausada(Boolean.FALSE);
        return publicacion;
    }

    private Publicacion crearPublicacionNueva(Cliente cliente){
        Publicacion publicacion = new Publicacion();
        publicacion.setNombre("Ladrillo");
        publicacion.setStock(100);
        publicacion.setPrecio(8.000);
        publicacion.setClientePublicacion(cliente);
        publicacion.setFechaInicioPublicacion(LocalDate.now());
        publicacion.setPublicacionPausada(Boolean.FALSE);
        return publicacion;
    }
}
