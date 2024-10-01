package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.modelo.Estado;
import com.tallerwebi.dominio.modelo.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ControladorProyecto {
    private ServicioPregunta servicioPregunta;
    private ServicioProyecto servicioProyecto;
    private ServicioEstado servicioEstado;


    @Autowired
    public ControladorProyecto(ServicioPregunta servicioPregunta, ServicioProyecto servicioProyecto, ServicioEstado servicioEstado) {
        this.servicioPregunta = servicioPregunta;
        this.servicioProyecto = servicioProyecto;
        this.servicioEstado = servicioEstado;

    }

    @RequestMapping(value = "/nuevo-proyecto", method = RequestMethod.GET)
    public ModelAndView mostrarFormularioNuevoProyecto() {
        Estado estadoNuevo = servicioEstado.obtenerEstadoPorNombre("nuevo");
        List<Proyecto> proyectosNuevos = servicioProyecto.obtenerProyectosPorEstado(estadoNuevo.getNombre());
        ModelAndView modelAndView = new ModelAndView("nuevo-proyecto");
        modelAndView.addObject("proyectos", proyectosNuevos);
        return modelAndView;
    }
    @RequestMapping(value = "/nuevo-proyecto", method = RequestMethod.POST)
    public String crearNuevoProyecto(@RequestParam("tipoProyecto") String tipoProyectoStr,
                                     @RequestParam("tipoTrabajo") String tipoTrabajo,
                                     @RequestParam("realizadoPor") String realizadoPor,
                                     @RequestParam("descripcionProyecto") String descripcion) {

        // Convertir a enum y crear un nuevo proyecto
        TipoProyecto tipoProyecto = TipoProyecto.valueOf(tipoProyectoStr.toUpperCase());
        Proyecto proyecto = new Proyecto();

        // Obtener y asignar estado "nuevo"
        Estado estadoNuevo = servicioEstado.obtenerEstadoPorNombre("nuevo");
        if (estadoNuevo == null) {
            estadoNuevo = new Estado("nuevo");
            servicioEstado.save(estadoNuevo);
        }

        proyecto.setTipoProyecto(tipoProyecto);
        proyecto.setTipoDeTrabajo(tipoTrabajo);
        proyecto.setRealizadoPor(realizadoPor);
        proyecto.setDescripcion(descripcion);
        proyecto.setEstado(estadoNuevo);
        // Guardar el proyecto
        servicioProyecto.guardarProyecto(proyecto);
        // Redirigir a la vista de nuevo proyecto (evita el reenvío de formularios en F5)
        return "redirect:/nuevo-proyecto";
    }

    // Agregar un comentario a un proyecto existente
    @RequestMapping(value = "/nuevo-comentario", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<String>> agregarComentario(@RequestParam("idProyecto") Long idProyecto,
                                                          @RequestParam("comentario") String comentario) {
        Proyecto proyecto = servicioProyecto.obtenerProyectoPorId(idProyecto);
        if (proyecto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Responder con 404 si no se encuentra el proyecto
        }
        proyecto.getComentarios().add(comentario);
        servicioProyecto.guardarProyecto(proyecto);  // Guardar el proyecto actualizado
        return new ResponseEntity<>(proyecto.getComentarios(), HttpStatus.OK);  // Responder con los comentarios actualizados y 200 OK
    }
    @RequestMapping(value = "/proyectos", method = RequestMethod.GET)
    public ModelAndView mostrarProyectos() {
        return new ModelAndView("mis-proyectos");

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
        return new ModelAndView("mis-materiales");

    }

    @RequestMapping(value = "/respuesta", method = RequestMethod.POST)
    public ModelAndView mostrarRespuesta() {;
        return new ModelAndView("mostrar-respuesta");
    }

}