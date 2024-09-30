package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Proyecto;
import com.tallerwebi.dominio.repositorio.RepositorioProyecto;
import com.tallerwebi.dominio.TipoProyecto;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Query;
import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioProyectoImplTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioProyecto repositorioProyecto;

    @BeforeEach
    public void init(){
        this.repositorioProyecto = new RepositorioProyectoImpl(sessionFactory);
    }

//    @Test
//    @Transactional
//    public void dadoQueExisteUnRepositorioProyectoCuandoGuardoUnproyectoEntoncesLoEncuentroEnLaBaseDeDatos(){
//        Proyecto proyecto = new Proyecto();
//        proyecto.setTipoProyecto(TipoProyecto.PISO);
//        proyecto.setNombre("Proyecto");
//        proyecto.setDescripcion("Proyecto");
//        proyecto.setUsuarioId(1L);
//
//        this.repositorioProyecto.guardar(proyecto);
//
//        String hql = "SELECT a FROM Proyecto a WHERE a.tipoProyecto = : tipoProyecto";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("tipoProyecto", TipoProyecto.PISO);
//        Proyecto proyectoObtenido = (Proyecto)query.getSingleResult();
//
//        assertThat(proyectoObtenido, equalTo(proyecto));
//    }
}
