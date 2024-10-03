package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.modelo.enums.EstadoProyecto;
import com.tallerwebi.dominio.modelo.Proyecto;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ControladorProyecto {
    private ServicioPregunta servicioPregunta;
    private ServicioProyecto servicioProyecto;


    @Autowired
    public ControladorProyecto(ServicioPregunta servicioPregunta, ServicioProyecto servicioProyecto) {
        this.servicioPregunta = servicioPregunta;
        this.servicioProyecto = servicioProyecto;

    }

    // Nuevo metodo que busca un proyecto por su ID
    @RequestMapping(value = "/proyecto/{id}", method = RequestMethod.GET)
    public ModelAndView obtenerProyectoPorId(@PathVariable("id") Long id) {
        Proyecto proyecto = servicioProyecto.obtenerProyectoPorId(id); // Llama al servicio para obtener el proyecto
        ModelAndView modelAndView = new ModelAndView("detalle-proyecto");
        modelAndView.addObject("proyecto", proyecto); // Pasa el proyecto a la vista
        return modelAndView;
    }

    // Método para mostrar el formulario para crear un nuevo proyecto
    @RequestMapping(value = "/nuevo-proyecto", method = RequestMethod.GET)
    public ModelAndView mostrarFormularioNuevoProyecto() {
        ModelAndView modelAndView = new ModelAndView("nuevo-proyecto");
        modelAndView.addObject("proyecto", new Proyecto()); // Crea un nuevo objeto Proyecto para el formulario
        return modelAndView;
    }

    // Método para procesar la creación de un nuevo proyecto
    @RequestMapping(value = "/nuevo-proyecto", method = RequestMethod.POST)
    public String crearNuevoProyecto(@ModelAttribute Proyecto proyecto) {
        servicioProyecto.guardarProyecto(proyecto); // Llama al servicio para guardar el proyecto
        return ("redirect:/proyectos"); // Redirige a la lista de proyectos después de guardar
}
    @RequestMapping(value = "/proyectos", method = RequestMethod.GET)
    public ModelAndView motrarMisPublicaciones(){
        return new ModelAndView("mis-proyectos");
    }

}