package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.persistence.Query;
import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})

public class RepositorioUsuarioImplTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    public void init(){
        this.repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);

    }

/*    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioCuandoGuardoUnProfesionalEntoncesLoEncuentraPorNombreYApellidoEnLaBaseDeDatos(){
        Profesional profesional = crearProfesional();
        this.sessionFactory.getCurrentSession().save(profesional);

        Usuario usuario = new Usuario();
        usuario.setProfesional(profesional);
        this.repositorioUsuario.guardar(usuario);

        String hql = "select u from Usuario u where u.profesional.nombre = :nombre AND u.profesional.apellido = :apellido";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Facundo Ezequiel");
        query.setParameter("apellido", "Guillen");
        Usuario usuarioObtenido = (Usuario)query.getSingleResult();

        assertThat(usuarioObtenido.getProfesional().getNombre(), equalTo(usuario.getProfesional().getNombre()));
        assertThat(usuarioObtenido.getProfesional().getApellido(), equalTo(usuario.getProfesional().getApellido()));
    }


    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioUsuarioCuandoGuardoUnClienteEntoncesLoEncuentraPorNombreYApellidoEnLaBaseDeDatos() {
        Cliente cliente = crearCliente();
        this.sessionFactory.getCurrentSession().save(cliente);

        Usuario usuario = new Usuario();
        usuario.setCliente(cliente);
        this.repositorioUsuario.guardar(usuario);

        String hql = "select u from Usuario u where u.cliente.nombre = :nombre AND u.cliente.apellido = :apellido";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Facundo Ezequiel");
        query.setParameter("apellido", "Guillen");
        Usuario usuarioObtenido = (Usuario)query.getSingleResult();

        assertThat(usuarioObtenido.getCliente().getNombre(), equalTo(usuario.getCliente().getNombre()));
        assertThat(usuarioObtenido.getCliente().getApellido(), equalTo(usuario.getCliente().getApellido()));

    }*/
/*
    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnClienteEnLaBaseDeDatosDeUsuarioBuscarClientePorDniYEncontrarloEnLaBaseDeDatos(){
        Cliente cliente = crearCliente();
        this.sessionFactory.getCurrentSession().save(cliente);


        Usuario usuario = new Usuario();
        usuario.setCliente(cliente);
        this.repositorioUsuario.guardar(usuario);


        String hql = "SELECT c FROM Cliente c WHERE c.dni = :dni";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("dni", 33333333);
        Cliente clienteObtenido = (Cliente) query.getSingleResult();

        assertThat(clienteObtenido.getNombre(), equalTo(cliente.getNombre()));
        assertThat(clienteObtenido.getApellido(), equalTo(cliente.getApellido()));
        assertThat(clienteObtenido.getDni(), equalTo(cliente.getDni()));

    }*/


    //Metodos
    private Profesional crearProfesional(){
        Profesional profesional = new Profesional();
        profesional.setNombre("Facundo Ezequiel");
        profesional.setApellido("Guillen");
        profesional.setEmail("holaquetal@gmail.com");
        profesional.setDni(33333333);
        return profesional;
    }

    private Cliente crearCliente(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Facundo Ezequiel");
        cliente.setApellido("Guillen");
        cliente.setEmail("holaquetal@gmail.com");
        cliente.setDni(33333333);
        return cliente;
    }

}
