package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioComentario;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioConsulta;
import com.tallerwebi.dominio.modelo.Comentario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorComentarioTest {

    private ControladorComentario controladorComentario;
    private ServicioComentario servicioComentario;
    private HttpServletRequest request;
    private HttpSession session;
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void init(){
        this.servicioComentario = mock(ServicioComentario.class);
        this.controladorComentario = new ControladorComentario( this.servicioComentario);
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HttpSession.class);
        this.redirectAttributes = mock(RedirectAttributes.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void agregarComentarioDeberiaAgregarComentarioCuandoUsuarioEsProfesional() throws UsuarioNoEncontradoException, UsuarioSinPermisosException, ConsultaNoEncontradaException {
        Long idUsuario = 1L;
        Long consultaId = 1L;
        Comentario comentario = new Comentario();

        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);

        doNothing().when(servicioComentario).agregarComentario(consultaId, idUsuario, comentario);

        ModelAndView modelAndView = controladorComentario.agregarComentarioAConsulta(comentario, consultaId, request, redirectAttributes);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/consultas"));
        verify(servicioComentario).agregarComentario(consultaId, idUsuario, comentario);
    }
    @Test
    public void agregarComentarioDeberiaMostrarMensajeDeErrorCuandoOcurreExcepcion() throws ConsultaNoEncontradaException, UsuarioNoEncontradoException, UsuarioSinPermisosException {
        Long idUsuario = 1L;
        Long consultaId = 1L;
        Comentario comentario = new Comentario();

        when(session.getAttribute("ID")).thenReturn(idUsuario);
        when(request.getSession(false)).thenReturn(session);

        doThrow(new ConsultaNoEncontradaException("Consulta no encontrada")).when(servicioComentario).agregarComentario(consultaId, idUsuario, comentario);

        ModelAndView modelAndView = controladorComentario.agregarComentarioAConsulta(comentario, consultaId, request, redirectAttributes);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/consultas"));
        verify(redirectAttributes).addFlashAttribute("mensaje", "Consulta no encontrada");
        verify(redirectAttributes).addFlashAttribute("tipoMensaje", "danger");
    }

    @Test
    public void agregarUtilDeberiaIncrementarContadorCuandoClienteReaccionaAlComentario() {
        Long comentarioId = 1L;
        Comentario comentario = new Comentario();
        comentario.setUseful(5);

        when(servicioComentario.buscarPorId(comentarioId)).thenReturn(comentario);

        ResponseEntity<?> response = controladorComentario.agregarUtil(comentarioId);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(6));
        verify(servicioComentario).actualizarComentario(comentario);
    }

    @Test
    public void agregarUtilDeberiaDevolverNotFoundDeHTTPSatatusCuandoComentarioNoEsEncontrado() {
        Long comentarioId = 1L;

        when(servicioComentario.buscarPorId(comentarioId)).thenReturn(null);

        ResponseEntity<?> response = controladorComentario.agregarUtil(comentarioId);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
        assertThat(response.getBody(), equalTo("Comentario no encontrado"));
    }
}
