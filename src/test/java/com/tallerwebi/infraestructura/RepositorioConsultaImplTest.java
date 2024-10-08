package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioConsultaImplTest {
    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioConsulta repositorioConsulta;

    @BeforeEach
    public void init() {
        this.repositorioConsulta = new RepositorioConsultaImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void crearUnaConsulta(){
        //preparacion
        Consulta consulta = new Consulta();
        //ejecucion
        this.repositorioConsulta.save(consulta);

        Long idConsultaGuardada = consulta.getId();

        Consulta consultaObtenida = this.sessionFactory.getCurrentSession()
                .createQuery("FROM Consulta WHERE id = :id", Consulta.class)
                .setParameter("id", idConsultaGuardada)
                .uniqueResult();
        assertThat(consultaObtenida, is(notNullValue()));
        assertThat(consultaObtenida.getId(), is(equalTo(consulta.getId())));
    }
    @Test
    @Transactional
    @Rollback
    public void queSeObtenganLasConsultasPorUnUsuario(){
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);

        List<Consulta> consultas = new ArrayList<>();
        Consulta consulta1 = new Consulta();
        consulta1.setUsuario(usuario);
        consultas.add(consulta1);

        Consulta consulta2 = new Consulta();
        consulta2.setUsuario(usuario);
        consultas.add(consulta2);

        Consulta consulta3 = new Consulta();
        consulta3.setUsuario(usuario);
        consultas.add(consulta3);

        for (Consulta consulta : consultas) {
            this.repositorioConsulta.save(consulta);
        }

        // Ejecución
        List<Consulta> consultasObtenidas = this.repositorioConsulta.obtenerConsultasByUsuario(usuario);

        // Verificación
        assertThat(consultasObtenidas, is(notNullValue()));
        assertThat(consultasObtenidas.size(), is(3)); // Comprobar que se obtuvieron 2 consultas
        assertThat(consultasObtenidas, containsInAnyOrder(consulta1, consulta2, consulta3));

    }

}
