package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.modelo.enums.EstadoProyecto;
import com.tallerwebi.dominio.modelo.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class ControladorProyecto {
    private ServicioPregunta servicioPregunta;
    private ServicioProyecto servicioProyecto;


    @Autowired
    public ControladorProyecto(ServicioPregunta servicioPregunta, ServicioProyecto servicioProyecto) {
        this.servicioPregunta = servicioPregunta;
        this.servicioProyecto = servicioProyecto;

    }

//    @RequestMapping(value = "/nuevo-proyecto", method = RequestMethod.GET)
//    public ModelAndView mostrarFormularioNuevoProyecto() {
//        List<Proyecto> proyectosNuevos = servicioProyecto.obtenerProyectosPorEstado(estadoNuevo.getNombre());
//        ModelAndView modelAndView = new ModelAndView("nuevo-proyecto");
//        modelAndView.addObject("proyectos", proyectosNuevos);
//        return modelAndView;
//    }


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

    // Agregar un comentario a un proyecto existente
//    @RequestMapping(value = "/nuevo-comentario", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<List<String>> agregarComentario(@RequestParam("idProyecto") Long idProyecto,
//                                                          @RequestParam("comentario") String comentario) {
//        Proyecto proyecto = servicioProyecto.obtenerProyectoPorId(idProyecto);
//        if (proyecto == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Responder con 404 si no se encuentra el proyecto
//        }
//        proyecto.getComentarios().add(comentario);
//        servicioProyecto.guardarProyecto(proyecto);  // Guardar el proyecto actualizado
//        return new ResponseEntity<>(proyecto.getComentarios(), HttpStatus.OK);  // Responder con los comentarios actualizados y 200 OK
//    }
//    @RequestMapping(value = "/proyectos", method = RequestMethod.GET)
//    public ModelAndView mostrarProyectos() {
//        return new ModelAndView("mis-proyectos");
//
//    }

//    @RequestMapping(value = "/preguntas", method = RequestMethod.POST)
//    public ModelAndView mostrarPreguntasSegunTipoProyecto(@RequestParam("tipoProyecto") String tipoProyectoStr) {
//        // Convertir a enum
//        TipoProyecto tipoProyecto = TipoProyecto.valueOf(tipoProyectoStr.toUpperCase());
//        List<Pregunta> preguntas = servicioPregunta.obtenerPreguntasPorTipoProyecto(tipoProyecto);
//
//        ModelMap modelo = new ModelMap();
//        modelo.put("tipoProyecto", tipoProyectoStr);
//        modelo.put("preguntas", preguntas);
//
//        return new ModelAndView("mostrar-preguntas", modelo);
//    }

//    @RequestMapping(value = "/materiales", method = RequestMethod.GET)
//    public ModelAndView mostrarMateriales() {
//        return new ModelAndView("mis-materiales");
//
//    }

//    @RequestMapping(value = "/respuesta", method = RequestMethod.POST)
//    public ModelAndView mostrarRespuesta() {;
//        return new ModelAndView("mostrar-respuesta");
//    }

}