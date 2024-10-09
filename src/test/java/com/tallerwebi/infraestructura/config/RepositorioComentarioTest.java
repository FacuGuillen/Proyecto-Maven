package com.tallerwebi.infraestructura.config;

import com.tallerwebi.dominio.excepcion.ComentarioInexistenteEnBaseDeDatos;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioComentario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.infraestructura.RepositorioComentarioImpl;
import com.tallerwebi.infraestructura.RepositorioConsultaImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioComentarioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioComentario repositorioComentario;
    private RepositorioConsulta repositorioConsulta;
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    public void init() {
        this.repositorioComentario = new RepositorioComentarioImpl(this.sessionFactory);
        this.repositorioConsulta = new RepositorioConsultaImpl(this.sessionFactory);
        this.repositorioUsuario = new RepositorioUsuarioImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void crearUnComentario() {
        //preparacion
        Comentario comentario = new Comentario();
        //ejecucion
        this.repositorioComentario.save(comentario);

        Long idComentarioGuardado = comentario.getId();

        Comentario comentarioObtenido = this.sessionFactory.getCurrentSession()
                .createQuery("FROM Comentario WHERE id = :id", Comentario.class)
                .setParameter("id", idComentarioGuardado)
                .uniqueResult();
        assertThat(comentarioObtenido, is(notNullValue()));
        assertThat(comentarioObtenido.getId(), is(equalTo(comentario.getId())));
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queSeObtenganLosComentariosDeUnaConsulta(){
//
//        Profesional profesional = new Profesional();
//        this.sessionFactory.getCurrentSession().save(profesional);
//
//        Comentario comentario = new Comentario();
//        Comentario comentario2 = new Comentario();
//        Comentario comentario3 = new Comentario();
//        List<Comentario> comentarios = new ArrayList<>();
//        comentarios.add(comentario);
//        comentarios.add(comentario2);
//        comentarios.add(comentario3);
//
//        Consulta consulta = new Consulta();
//        consulta.setUsuario(profesional);
//        consulta.setComentarios(comentarios);
//
//        // Ejecución
//        List<Comentario> comentariosObtenios = this.repositorioComentario.getByConsulta(consulta);
//
//        // Verificación
//        assertThat(comentariosObtenios, is(notNullValue()));
//        assertThat(comentariosObtenios.size(), is(3)); // Comprobar que se obtuvieron 2 consultas
//
//    }

    @Test
    @Transactional
    @Rollback
    public void queSiSeHaceUnSoloComentarioSePuedaObtener() {
        // Crear una consulta
        Consulta consulta = new Consulta();
        repositorioConsulta.save(consulta);

        Profesional profesional = new Profesional();
        repositorioUsuario.guardar(profesional);

        Comentario comentario = new Comentario();
        comentario.setDescripcion("Este es un comentario de prueba");
        comentario.setUsuario(profesional);
        comentario.setConsulta(consulta);

        repositorioComentario.save(comentario);

        List<Comentario> comentarios = repositorioComentario.getByConsulta(consulta);

        assertThat(comentarios, is(not(empty())));
        assertThat(comentarios.get(0).getDescripcion(), is("Este es un comentario de prueba"));  // Verificar el contenido del comentario
        assertThat(comentarios.get(0).getUsuario(), is(profesional));  // Verificar que el comentario pertenezca al profesional asociado
    }

    @Test
    @Transactional
    @Rollback
    public void queSeObtenganLosComentariosDeDosProfesionalesDiferentesDeUnaConsulta() {
        Profesional profesional1 = new Profesional();
        profesional1.setNombre("Profesional Test");
        repositorioUsuario.guardar(profesional1);

        Profesional profesional2 = new Profesional();
        profesional2.setNombre("Profesional Test2");
        repositorioUsuario.guardar(profesional2);

        // Crear una consulta de prueba
        Consulta consulta = new Consulta();
        consulta.setDescripcion("Consulta de prueba");
        repositorioConsulta.save(consulta);

        // Crear comentarios de prueba
        Comentario comentario1 = new Comentario();
        comentario1.setUsuario(profesional1);
        comentario1.setConsulta(consulta);
        comentario1.setDescripcion("Comentario 1");
        repositorioComentario.save(comentario1);

        Comentario comentario2 = new Comentario();
        comentario2.setUsuario(profesional2);
        comentario2.setConsulta(consulta);
        comentario2.setDescripcion("Comentario 2");
        repositorioComentario.save(comentario2);

        // Act: Obtener comentarios por consulta
        List<Comentario> comentarios = repositorioComentario.getByConsulta(consulta);

        // Assert con Hamcrest
        assertThat(comentarios, is(not(empty())));
        assertThat(comentarios, hasSize(2)); // Verificar el tamaño de la lista

    }

    @Test
    @Transactional
    @Rollback
    public void queSeObtenganLosComentariosDeDosProfesionalesDistintosConPuntajesDistintosDeUnaConsultaYElPrimeroSeaElMejorPuntuado() {
        Profesional profesional1 = new Profesional();
        profesional1.setNombre("Profesional Test");
        profesional1.setCalificacion(5);
        repositorioUsuario.guardar(profesional1);

        Profesional profesional2 = new Profesional();
        profesional2.setNombre("Profesional Test2");
        profesional2.setCalificacion(10);
        repositorioUsuario.guardar(profesional2);

        // Crear una consulta de prueba
        Consulta consulta = new Consulta();
        consulta.setDescripcion("Consulta de prueba");
        repositorioConsulta.save(consulta);

        // Crear comentarios de prueba
        Comentario comentario1 = new Comentario();
        comentario1.setUsuario(profesional1);
        comentario1.setConsulta(consulta);
        comentario1.setDescripcion("Comentario 1");
        comentario1.setFechaCreacion(LocalDateTime.now().minusMinutes(1));
        repositorioComentario.save(comentario1);

        Comentario comentario2 = new Comentario();
        comentario2.setUsuario(profesional2);
        comentario2.setConsulta(consulta);
        comentario2.setDescripcion("Comentario 2");
        comentario2.setFechaCreacion(LocalDateTime.now().minusMinutes(2));
        repositorioComentario.save(comentario2);

        // Act: Obtener comentarios por consulta
        List<Comentario> comentarios = repositorioComentario.getByConsulta(consulta);

        // Assert con Hamcrest
        assertThat(comentarios, is(not(empty())));
        assertThat(comentarios, hasSize(2)); // Verificar el tamaño de la lista

        // Verificar que el primer comentario en la lista tiene el texto "Comentario 2"
        assertThat(comentarios.get(0).getDescripcion(), is("Comentario 2"));

        // Verificar que el segundo comentario en la lista tiene el texto "Comentario 1"
        assertThat(comentarios.get(1).getDescripcion(), is("Comentario 1"));
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaActualizarUnComentario(){
        // Crear una consulta
        Consulta consulta = new Consulta();
        repositorioConsulta.save(consulta);

        Profesional profesional = new Profesional();
        repositorioUsuario.guardar(profesional);

        Comentario comentario = new Comentario();
        comentario.setDescripcion("Este es un comentario de prueba");
        comentario.setUsuario(profesional);
        comentario.setConsulta(consulta);

        repositorioComentario.save(comentario);

        comentario.setDescripcion("Este Comentario Fue Actualizado");
        repositorioComentario.actualizar(comentario);

        List<Comentario> comentarios = repositorioComentario.getByConsulta(consulta);

        assertThat(comentarios, hasSize(1));
        assertThat(comentarios.get(0).getDescripcion(), is("Este Comentario Fue Actualizado"));

    }

    @Test
    @Transactional
    @Rollback
    public void queCuandoIntentoBuscarUnComentarioQueNoExisteEnLaBaseDeDatosArrojeUnaExcepcion() {

        assertThrows(ComentarioInexistenteEnBaseDeDatos.class, () ->{
            repositorioComentario.findById(1L);
        });



    }

}
