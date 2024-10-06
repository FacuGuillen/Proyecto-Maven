package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioProfesional;
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
import static org.hamcrest.Matchers.*;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})


public class RepositorioProfesionalImplTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioProfesional repositorioProfesional;

    @BeforeEach
    public void init(){
        this.repositorioProfesional = new RepositorioProfesionalImpl(sessionFactory);
    }


    @Test
    @Transactional
    @Rollback

    public void dadoQueExisteUnRepositorioProfesionalCuandoGuardoUnProfesionalEntoncesLoEncuentroEnLaBaseDeDatos(){
        Profesional profesional = new Profesional();
        profesional.setNombre("Facundo Guillen");

        this.repositorioProfesional.guardar(profesional);
        String hql = "from Profesional where nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Facundo Guillen");
        Profesional profesionalObtenido = (Profesional) query.getSingleResult();

        assertThat(profesionalObtenido, equalTo(profesional));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioProfesionalCuandoGuardo3ProfesionalesLosEncuentroEnLaBaseDeDatos(){
        Profesional profesional = new Profesional();
        profesional.setNombre("Facundo Guillen");
        Profesional profesional2 = new Profesional();
        profesional2.setNombre("Lautaro Rossi");
        Profesional profesional3 = new Profesional();
        profesional3.setNombre("Matias Martin");
        this.repositorioProfesional.guardar(profesional);
        this.repositorioProfesional.guardar(profesional2);
        this.repositorioProfesional.guardar(profesional3);

        List<Profesional> profesionalesObtenidos = this.repositorioProfesional.obtener();

        int cantidadEsperada = 3;
        assertThat(profesionalesObtenidos.size(), equalTo(cantidadEsperada));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioProfesionalCuandoGuardoUnPresionalYDespuesLoEliminoEntoncesNoLoEcuentraEnLaBaseDeDatos() {
        // Guardar un profesional
        Profesional profesional = guardarProfesional();

        // Eliminar el profesional
        this.repositorioProfesional.eliminar(profesional);

        // Crear la consulta para buscar el profesional eliminado
        String hql = "from Profesional where nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Lionel Scaloni");

        // Intentar obtener el resultado
        Profesional profesionalObtenido = null;
        try {
            profesionalObtenido = (Profesional) query.getSingleResult();
        } catch (NoResultException e) {
            // No se espera que se lance esta excepción, así que simplemente la ignoramos
        }

        // Verificar que el profesional obtenido es null
        assertThat(profesionalObtenido, is(nullValue())); // Verifica que sea null
    }


    public Profesional guardarProfesional() { // Cambiar el retorno a Profesional
        Profesional profesional = new Profesional();
        profesional.setNombre("Lionel Scaloni");

        this.repositorioProfesional.guardar(profesional);
        return profesional; // Retornar el profesional guardado
    }
}
