package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.modelo.enums.EstadoProyecto;
import com.tallerwebi.dominio.modelo.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


    @RequestMapping(value = "/nuevo-proyecto", method = RequestMethod.POST)
    public String crearNuevoProyecto(@RequestParam("nombreProyecto") String nombreProyecto,
                                     @RequestParam("descripcionProyecto") String descripcion,
                                     @RequestParam("fechaInicioProyecto") LocalDate fechaInicioProyecto,
                                     @RequestParam("modoTrabajoProfesional") Boolean trabajoPresencial) {

        //EstadoProyecto estadoProyecto = EstadoProyecto.valueOf(estadoProyectoStr.toUpperCase());
        Proyecto proyecto = new Proyecto();

        proyecto.setNombreProyecto(nombreProyecto);
        proyecto.setDescripcion(descripcion);
        proyecto.setFechaInicioProyecto(fechaInicioProyecto);
        proyecto.setTrabajoPresencial(trabajoPresencial);
        proyecto.setEstadoProyecto(EstadoProyecto.POR_INICIAR);

        // Guardar el proyecto
        servicioProyecto.guardarProyecto(proyecto);
        // Redirigir a la vista de nuevo proyecto (evita el reenvío de formularios en F5)
        return "redirect:/nuevo-proyecto";
    }

}