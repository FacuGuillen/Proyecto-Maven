package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.enums.EstadoProyecto;
import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.modelo.Proyecto;
import com.tallerwebi.dominio.repositorio.*;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static com.tallerwebi.infraestructura.RepositorioClienteImplTest.crearClienteConDatos;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioProyectoImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCliente repositorioCliente;
    private RepositorioProfesional repositorioProfesional;
    private RepositorioProyecto repositorioProyecto;


    @BeforeEach
    public void init(){
        this.repositorioCliente = new RepositorioClienteImpl(sessionFactory);
        this.repositorioProfesional = new RepositorioProfesionalImpl(sessionFactory);
        this.repositorioProyecto = new RepositorioProyectoImpl(sessionFactory);
    }



    @Test
    @Transactional
    @Rollback
    public void proyectoCreadoConUnClienteYUnProfesional(){
        Cliente cliente = crearClienteConDatos();
        Profesional profesional = crearProfesionalConDatos();
        Proyecto proyecto = crearProyecto(cliente, profesional);

        repositorioCliente.guardar(cliente);
        repositorioProfesional.guardar(profesional);
        repositorioProyecto.guardar(proyecto);

        Proyecto proyectoObtenido = repositorioProyecto.obtenerPorId(proyecto.getId());

        assertThat(proyectoObtenido.getNombreProyecto(), equalTo(proyecto.getNombreProyecto()));
        assertThat(proyectoObtenido.getTrabajoPresencial(), equalTo(proyecto.getTrabajoPresencial()));
        assertThat(proyectoObtenido.getCliente(), equalTo(cliente));
        assertThat(proyectoObtenido.getProfesional(), equalTo(profesional));

    }


    // Metodos
    private Proyecto crearProyecto(Cliente cliente, Profesional profesional){
        Proyecto proyecto = new Proyecto();
        proyecto.setNombreProyecto("Romper la casa entera");
        proyecto.setEstadoProyecto(EstadoProyecto.POR_INICIAR);
        proyecto.setFechaInicioProyecto(LocalDate.of(2024, 10, 15));
        proyecto.setTrabajoPresencial(false);
        proyecto.setCliente(cliente);
        proyecto.setProfesional(profesional);
        return proyecto;
    }

    private Profesional crearProfesionalConDatos() {
        Profesional profesional = new Profesional();
        profesional.setNombre("Lionel Sebastian");
        profesional.setApellido("Scaloni");
        profesional.setEmail("LionesScaloniCampeonDelMundoQatar2022");
        profesional.setTelefono("18122022");
        return profesional;
    }
}
