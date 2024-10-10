package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.ServicioComentarioImpl;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioComentario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioComentario;
import com.tallerwebi.dominio.modelo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServicioComentarioTest {
    private ServicioComentario servicioComentario;
    private RepositorioComentario repositorioComentario;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioConsulta repositorioConsulta;

    @BeforeEach
    public void setUp() {
        repositorioComentario = Mockito.mock(RepositorioComentario.class);
        repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        repositorioConsulta = Mockito.mock(RepositorioConsulta.class);
        servicioComentario = new ServicioComentarioImpl(repositorioComentario, repositorioUsuario, repositorioConsulta);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeLanzaUsuarioNoEncontradoExceptionCuandoElUsuarioNoExiste() {
        Long consultaId = 1L;
        Long idUsuarioInexistente = 999L;
        Comentario comentario = new Comentario();
        comentario.setDescripcion("Comentario de prueba");

        when(repositorioUsuario.findById(idUsuarioInexistente)).thenReturn(null);

        Consulta consulta = new Consulta();
        when(repositorioConsulta.findById(consultaId)).thenReturn(consulta);

        assertThrows(UsuarioNoEncontradoException.class, () -> {
            servicioComentario.agregarComentario(consultaId, idUsuarioInexistente, comentario);
        });

        verify(repositorioUsuario).findById(idUsuarioInexistente);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeLanzaConsultaNoEncontradaExceptionCuandoLaConsultaNoExiste() {
        Profesional profesional = new Profesional();
        profesional.setNombre("Profesional Test");
        profesional.setCalificacion(5);

        Long consultaIdInexistente = 999L;
        Long idUsuario = profesional.getId();
        Comentario comentario = new Comentario();
        comentario.setDescripcion("Comentario de prueba");

        when(repositorioUsuario.findById(idUsuario)).thenReturn(profesional);

        when(repositorioConsulta.findById(consultaIdInexistente)).thenReturn(null);

        assertThrows(ConsultaNoEncontradaException.class, () -> {
            servicioComentario.agregarComentario(consultaIdInexistente, idUsuario, comentario);
        });

        verify(repositorioConsulta).findById(consultaIdInexistente);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeLanzaUsuarioSinPermisosExceptionCuandoElUsuarioNoEsProfesional() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Test");

        Consulta consulta = new Consulta();
        consulta.setDescripcion("Consulta de prueba");

        Long consultaId = consulta.getId();
        Long idUsuario = cliente.getId();
        Comentario comentario = new Comentario();
        comentario.setDescripcion("Comentario de prueba");

        when(repositorioUsuario.findById(idUsuario)).thenReturn(cliente);

        when(repositorioConsulta.findById(consultaId)).thenReturn(consulta);

        assertThrows(UsuarioSinPermisosException.class, () -> {
            servicioComentario.agregarComentario(consultaId, idUsuario, comentario);
        });

        verify(repositorioUsuario).findById(idUsuario);
        verify(repositorioConsulta).findById(consultaId);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeAgregaComentarioCuandoElUsuarioEsProfesionalYLaConsultaExiste() throws Exception {
        Profesional profesional = new Profesional();
        profesional.setNombre("Profesional Test");
        profesional.setCalificacion(5);
        when(repositorioUsuario.findById(anyLong())).thenReturn(profesional);

        Long idUsuario = 1L;

        Consulta consulta = new Consulta();
        consulta.setDescripcion("Consulta de prueba");
        consulta.setId(1L);
        when(repositorioConsulta.findById(anyLong())).thenReturn(consulta);

        Comentario comentario = new Comentario();
        comentario.setDescripcion("Comentario de prueba");

        List<Comentario> listaComentarios = new ArrayList<>();
        listaComentarios.add(comentario);
        when(repositorioComentario.getByConsulta(consulta)).thenReturn(listaComentarios);

        servicioComentario.agregarComentario(consulta.getId(), idUsuario, comentario);

        List<Comentario> comentarios = repositorioComentario.getByConsulta(consulta);
        assertThat(comentarios, is(not(empty())));
        assertThat(comentarios, hasSize(1));
        assertThat(comentarios.get(0).getDescripcion(), is("Comentario de prueba"));

        verify(repositorioComentario).save(comentario);
    }

}
