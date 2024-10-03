package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioConsulta;
import com.tallerwebi.dominio.modelo.enums.TipoConsulta;
import com.tallerwebi.dominio.modelo.enums.TipoTrabajo;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.modelo.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorForo {
    private ServicioConsulta servicioConsulta;

    @Autowired
    public ControladorForo( ServicioConsulta servicioConsulta){
        this.servicioConsulta = servicioConsulta;
    }
    @RequestMapping(value = "/consultas", method = RequestMethod.GET)
    public ModelAndView mostrarForo(
            HttpServletRequest request){
        if (request == null || request.getSession() == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        Long idUsuario = (Long) request.getSession().getAttribute("ID");
        List<Consulta> consultas = null;
        try {
            consultas = servicioConsulta.listarConsultasByIdUsuario(idUsuario);
        } catch (UsuarioNoEncontrado e) {
            return new ModelAndView("redirect:/login");
        }
        model.put("consulta", new Consulta());
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
        if (request == null || request.getSession() == null) {

            return ("redirect:/logout");
        }
        Long idUsuario = (Long) request.getSession().getAttribute("ID");
        try {
            servicioConsulta.agregarConsulta(idUsuario, consulta);
        } catch (UsuarioNoEncontrado e) {
            return ("redirect:/logout");
        }
        return ("redirect:/consultas");
    }
}
