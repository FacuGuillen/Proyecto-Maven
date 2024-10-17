package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioComentario;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioConsulta;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Consulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorConsultaImplTest {
    private ControladorConsulta controladorConsulta;
    private ServicioConsulta servicioConsulta;
    private ServicioComentario servicioComentario;
    private HttpServletRequest request;
    private HttpSession session;
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void init(){
        this.servicioConsulta = mock(ServicioConsulta.class);
        this.servicioComentario = mock(ServicioComentario.class);
        this.controladorConsulta = new ControladorConsulta(this.servicioConsulta, this.servicioComentario);
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        this.redirectAttributes = mock(RedirectAttributes.class);
        when(request.getSession()).thenReturn(session);
    }
    @Test
    public void mostrarForoDeberiaRedirigirALoginCuandoSessionEsNull() {
        when(request.getSession()).thenReturn(null);

        ModelAndView modelAndView = controladorConsulta.mostrarForo(request);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void mostrarForoDeberiaListarConsultasCuandoUsuarioEsteLogueado() throws UsuarioNoEncontradoException {
        Long idUsuario = 1L;
        List<Consulta> consultas = new ArrayList<>();
        consultas.add(new Consulta());

        // Simula que el usuario está en la sesión
        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);

        // Simula el comportamiento de servicioConsulta
        when(servicioConsulta.getListado()).thenReturn(consultas);

        ModelAndView modelAndView = controladorConsulta.mostrarForo(request);

        assertThat(modelAndView.getViewName(), equalTo("foro"));
        assertThat(modelAndView.getModel().get("consultas"), is(consultas));
        verify(servicioConsulta).getListado();
    }

    @Test
    public void crearConsultaDeberiaRedirigirALoginCuandoSessionEsNull() {
        when(session.getAttribute("ID")).thenReturn(null);

        String result = controladorConsulta.crearConsulta(new Consulta(), request, redirectAttributes);

        assertThat(result, equalTo("redirect:/login"));
    }

    @Test
    public void crearConsultaDeberiaRedirigirAConsultasCuandoUsurioEsCliente() throws UsuarioNoEncontradoException {
        Long idUsuario = 1L;
        Consulta consulta = new Consulta();

        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);

        doNothing().when(servicioConsulta).agregarConsulta(idUsuario, consulta);

        String result = controladorConsulta.crearConsulta(consulta, request, redirectAttributes);

        assertThat(result, equalTo("redirect:/consultas"));
        verify(servicioConsulta).agregarConsulta(idUsuario, consulta);
    }

    @Test
    public void mostrarForoDeberiaRedirigirALoginCuandoUsuarioNoEsEncontrado() throws UsuarioNoEncontradoException {
        Long idUsuario = 1L;

        when(session.getAttribute("ID")).thenReturn(idUsuario);

        when(servicioConsulta.getListado()).thenThrow(new UsuarioNoEncontradoException("El usuario no fue encontrado"));

        ModelAndView modelAndView = controladorConsulta.mostrarForo(request);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }
    @Test
    public void crearConsultaDeberiaRedirigirConMensajeDeErrorCuandoUsuarioEsProfesional() throws UsuarioNoEncontradoException, UsuarioSinPermisosException {
        Long idUsuario = 1L;
        Consulta consulta = new Consulta();

        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);

        doThrow(new UsuarioSinPermisosException("Solo los clientes pueden crear consultas")).when(servicioConsulta).agregarConsulta(idUsuario, consulta);

        String result = controladorConsulta.crearConsulta(consulta, request, redirectAttributes);

        assertThat(result, equalTo("redirect:/consultas"));
        verify(redirectAttributes).addFlashAttribute("mensaje", "Solo los clientes pueden crear consultas");
        verify(redirectAttributes).addFlashAttribute("tipoMensaje", "danger");
    }

}
