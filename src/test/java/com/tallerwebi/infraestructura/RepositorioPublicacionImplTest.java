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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import javax.persistence.Query;
import java.time.LocalDate;

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




    // Metodos
    private Cliente crearCliente(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Lionel Andres");
        cliente.setApellido("Messi");
        cliente.setEmail("LeoMessiCampeonDelMundoQatar2022");
        cliente.setTelefono("18122022");
        return cliente;
    }

    private Publicacion crearPublicacion(Cliente cliente){
        Publicacion publicacion = new Publicacion();
        publicacion.setNombre("Cemento");
        publicacion.setStock(4);
        publicacion.setPrecio(15.000);
        publicacion.setClientePublicacion(cliente);
        publicacion.setFechaInicioPublicacion(LocalDate.now());
        return publicacion;
    }
}
