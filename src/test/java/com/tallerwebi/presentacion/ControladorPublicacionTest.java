package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.implementacion.interfaces.ServicioCliente;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Publicacion;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ControladorPublicacionTest {
    private ControladorPublicacion controladorPublicacion;
    private ServicioPublicacion servicioPublicacion;
    private ServicioCliente servicioCliente;
    private HttpServletRequest request;
    private HttpSession session;
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void init() {
        this.servicioPublicacion = mock(ServicioPublicacion.class);
        this.servicioCliente = mock(ServicioCliente.class);
        this.controladorPublicacion = new ControladorPublicacion(this.servicioPublicacion, this.servicioCliente);
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        this.redirectAttributes = mock(RedirectAttributes.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void ofertarMaterialesDeberiaRetornarVistaCorrecta() {
        ModelAndView modelAndView = controladorPublicacion.ofertarMateriales();

        assertThat(modelAndView.getViewName(), equalTo("ofertar-material"));
    }

    @Test
    public void guardarPublicacionDeberiaRedirigirALoginCuandoSessionEsNull() {
        when(request.getSession(false)).thenReturn(null);

        ModelAndView modelAndView = controladorPublicacion.guardarPublicacion("Publicación Test", 100.0, 10, request);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void guardarPublicacionDeberiaRedirigirALoginCuandoClienteEsNull() {
        Long idUsuario = 1L;
        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);
        when(servicioCliente.buscarPorId(idUsuario)).thenReturn(null);

        ModelAndView modelAndView = controladorPublicacion.guardarPublicacion("Publicación Test", 100.0, 10, request);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void guardarPublicacionDeberiaGuardarPublicacionCorrectamente() {
        Long idUsuario = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(idUsuario);
        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);
        when(servicioCliente.buscarPorId(idUsuario)).thenReturn(cliente);

        ModelAndView modelAndView = controladorPublicacion.guardarPublicacion("Publicación Test", 100.0, 10, request);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/misPublicaciones"));
        verify(servicioPublicacion).guardarPublicacion(any(Publicacion.class));
    }

    @Test
    public void mostrarMisPublicacionesDeberiaRedirigirALoginCuandoSessionEsNull() {
        when(request.getSession(false)).thenReturn(null);

        ModelAndView modelAndView = controladorPublicacion.mostrarMisPublicaciones(request);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void mostrarMisPublicacionesDeberiaRedirigirALoginCuandoClienteNoEsEncontrado() {
        Long idUsuario = 1L;
        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);
        when(servicioCliente.buscarPorId(idUsuario)).thenReturn(null);

        ModelAndView modelAndView = controladorPublicacion.mostrarMisPublicaciones(request);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void mostrarMisPublicacionesDeberiaMostrarPublicacionesCorrectamente() {
        Long idUsuario = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(idUsuario);
        List<Publicacion> publicaciones = new ArrayList<>();
        publicaciones.add(new Publicacion());

        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);
        when(servicioCliente.buscarPorId(idUsuario)).thenReturn(cliente);
        when(servicioPublicacion.getListadoPublicacionPorCliente(cliente)).thenReturn(publicaciones);

        ModelAndView modelAndView = controladorPublicacion.mostrarMisPublicaciones(request);

        assertThat(modelAndView.getViewName(), equalTo("mis-publicaciones"));
        assertThat(modelAndView.getModel().get("publicaciones"), is(publicaciones));
        verify(servicioPublicacion).getListadoPublicacionPorCliente(cliente);
    }

    @Test
    public void eliminarPublicacionDeberiaRedirigirCorrectamente() {
        Long publicacionId = 1L;

        ModelAndView modelAndView = controladorPublicacion.eliminarPublicacion(publicacionId);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/misPublicaciones"));
        verify(servicioPublicacion).eliminarPublicacion(publicacionId);
    }

    // Agrega más pruebas según sea necesario
}
