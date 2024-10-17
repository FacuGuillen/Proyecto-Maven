package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioComentario;
import com.tallerwebi.dominio.modelo.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorComentario {
    private ServicioComentario servicioComentario;

    @Autowired
    public ControladorComentario(ServicioComentario servicioComentario) {
        this.servicioComentario = servicioComentario;
    }

    @RequestMapping(value = "/agregarComentario", method = RequestMethod.POST)
    public ModelAndView agregarComentarioAConsulta(
            @ModelAttribute("comentario") Comentario comentario,
            @RequestParam("consultaId") Long consultaId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes){
        if(!validarSesion(request)){
            return new ModelAndView("redirect:/login");
        }
        Long idUsuario = (Long) request.getSession().getAttribute("ID");
        try {
            servicioComentario.agregarComentario(consultaId, idUsuario, comentario);
        } catch (ConsultaNoEncontradaException | UsuarioNoEncontradoException | UsuarioSinPermisosException e) {
            redirectAttributes.addFlashAttribute("mensaje",  e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return new ModelAndView("redirect:/consultas");
    }

    @PostMapping("/agregarUtil")
    public ResponseEntity<?> agregarUtil(@RequestParam Long comentarioId) {
        Comentario comentario = servicioComentario.buscarPorId(comentarioId);
        if (comentario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario no encontrado");
        }

        comentario.setUseful(comentario.getUseful() + 1);
        servicioComentario.actualizarComentario(comentario);
        return ResponseEntity.ok(comentario.getUseful());
    }

    private boolean validarSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("ID") != null;
    }
}
