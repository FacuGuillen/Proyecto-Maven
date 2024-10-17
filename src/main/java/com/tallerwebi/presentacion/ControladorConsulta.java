package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioComentario;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioConsulta;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.enums.TipoConsulta;
import com.tallerwebi.dominio.modelo.enums.TipoTrabajo;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorConsulta {
    private ServicioConsulta servicioConsulta;
    private ServicioComentario servicioComentario;

    @Autowired
    public ControladorConsulta(ServicioConsulta servicioConsulta, ServicioComentario servicioComentario){
        this.servicioConsulta = servicioConsulta;
        this.servicioComentario = servicioComentario;
    }
    @RequestMapping(value = "/consultas", method = RequestMethod.GET)
    public ModelAndView mostrarForo(
            HttpServletRequest request){

        ModelMap model = new ModelMap();
        List<Consulta> consultas;
        if(!validarSesion(request)){
            return new ModelAndView("redirect:/login");
        }
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
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
            ){
        if(!validarSesion(request)){
            return ("redirect:/login");
        }
        Long idUsuario = (Long) request.getSession().getAttribute("ID");

        try {
            servicioConsulta.agregarConsulta(idUsuario, consulta);
            redirectAttributes.addFlashAttribute("mensaje", "Consulta agregado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (UsuarioNoEncontradoException | UsuarioSinPermisosException e) {
            redirectAttributes.addFlashAttribute("mensaje",  e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return ("redirect:/consultas");
    }
    private boolean validarSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("ID") != null;
    }
}
