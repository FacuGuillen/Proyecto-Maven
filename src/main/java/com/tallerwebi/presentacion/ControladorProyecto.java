package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pregunta;
import com.tallerwebi.dominio.ServicioPregunta;
import com.tallerwebi.dominio.TipoProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorProyecto {
    private ServicioPregunta servicioPregunta;


    @Autowired
    public ControladorProyecto(ServicioPregunta servicioPregunta) {
        this.servicioPregunta = servicioPregunta;

    }

    @RequestMapping(value = "/nuevo-proyecto", method = RequestMethod.GET)
    public ModelAndView crearProyecto() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("nuevo-proyecto", modelo);
    }

    @RequestMapping(value = "/proyectos", method = RequestMethod.GET)
    public ModelAndView mostrarProyectos() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("mis-proyectos", modelo);

    }

    @RequestMapping(value = "/preguntas", method = RequestMethod.POST)
    public ModelAndView mostrarPreguntasSegunTipoProyecto(@RequestParam("tipoProyecto") String tipoProyectoStr) {
        // Convertir a enum
        TipoProyecto tipoProyecto = TipoProyecto.valueOf(tipoProyectoStr.toUpperCase());
        List<Pregunta> preguntas = servicioPregunta.obtenerPreguntasPorTipoProyecto(tipoProyecto);

        ModelMap modelo = new ModelMap();
        modelo.put("tipoProyecto", tipoProyectoStr);
        modelo.put("preguntas", preguntas);

        return new ModelAndView("mostrar-preguntas", modelo);
    }

    @RequestMapping(value = "/materiales", method = RequestMethod.GET)
    public ModelAndView mostrarMateriales() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("mis-materiales", modelo);

    }

}