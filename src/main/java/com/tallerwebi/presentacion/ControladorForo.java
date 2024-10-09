package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioComentario;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioConsulta;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.enums.TipoConsulta;
import com.tallerwebi.dominio.modelo.enums.TipoTrabajo;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorForo {
    private ServicioConsulta servicioConsulta;
    private ServicioComentario servicioComentario;

    @Autowired
    public ControladorForo( ServicioConsulta servicioConsulta, ServicioComentario servicioComentario){
        this.servicioConsulta = servicioConsulta;
        this.servicioComentario = servicioComentario;
    }
    @RequestMapping(value = "/consultas", method = RequestMethod.GET)
    public ModelAndView mostrarForo(
            HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        Long idUsuario = (Long) request.getSession().getAttribute("ID");
        List<Consulta> consultas = new ArrayList<>();
        try {
            consultas = servicioConsulta.getListado();
            System.out.println("Consultas: " + consultas);
            for (Consulta consulta : consultas) {
                List<Comentario> comentarios = servicioComentario.listarComentariosByConsulta(consulta);
                consulta.setComentarios(comentarios);
            }

        } catch (UsuarioNoEncontradoException e) {
            return new ModelAndView("redirect:/login");
        }
        model.put("consulta", new Consulta());
        model.put("comentario", new Comentario());
        model.put("tipoConsultas", TipoConsulta.values());
        model.put("tipoTrabajos", TipoTrabajo.values());
        model.put("consultas", consultas);
        return new ModelAndView("foro", model);
    }

    @RequestMapping(value = "/crear-consulta", method = RequestMethod.POST)
    public String crearConsulta(
            @ModelAttribute("consulta") Consulta consulta,
            HttpServletRequest request
            ){
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ID") == null) {

            return ("redirect:/login");
        }
        Long idUsuario = (Long) request.getSession().getAttribute("ID");
        try {
            servicioConsulta.agregarConsulta(idUsuario, consulta);
        } catch (UsuarioNoEncontradoException e) {
            return ("redirect:/login");
        }
        return ("redirect:/consultas");
    }

    @RequestMapping(value = "/agregarComentario", method = RequestMethod.POST)
    public ModelAndView agregarComentarioAConsulta(
            @ModelAttribute("comentario") Comentario comentario,
            @RequestParam("consultaId") Long consultaId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes){

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }
        Long idUsuario = (Long) request.getSession().getAttribute("ID");

        try {
            servicioComentario.agregarComentario(consultaId, idUsuario, comentario);
            redirectAttributes.addFlashAttribute("mensaje", "Comentario agregado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (ConsultaNoEncontradaException | UsuarioNoEncontradoException | UsuarioSinPermisosException e) {
            redirectAttributes.addFlashAttribute("mensaje",  e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return new ModelAndView("redirect:consultas");
    }
}
