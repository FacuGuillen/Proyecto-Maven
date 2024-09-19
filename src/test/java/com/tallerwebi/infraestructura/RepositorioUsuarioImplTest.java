package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.repositorio.RepositorioProfesional;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hamcrest.Matcher;
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

    @Test
    @Transactional
    @Rollback

    public void dadoQueExisteUnRepositorioUsuarioCuandoGuardoUnProfesionalEntoncesLoEncuentraEnLaBaseDeDatos(){
        Profesional profesional = new Profesional();
        profesional.setNombre("Facundo Guillen");
        this.sessionFactory.getCurrentSession().save(profesional);

        Usuario usuario = new Usuario();
        usuario.setProfesional(profesional);
        this.repositorioUsuario.guardar(usuario);

        String hql = "select u from Usuario u where u.profesional.nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Facundo Guillen");
        Usuario usuarioObtenido = (Usuario)query.getSingleResult();

        assertThat(usuarioObtenido, equalTo(usuario));
    }



}
