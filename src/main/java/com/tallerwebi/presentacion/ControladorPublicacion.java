package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.implementacion.interfaces.ServicioCliente;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller //Indica que es responsable de manejar las solicitudes web.
public class ControladorPublicacion {

    private final ServicioPublicacion servicioPublicacion;
    private final ServicioCliente servicioCliente;


    @Autowired
    public ControladorPublicacion(ServicioPublicacion servicioPublicacion, ServicioCliente servicioCliente) {
        this.servicioPublicacion = servicioPublicacion;
        this.servicioCliente = servicioCliente;
    }


    @RequestMapping(value = "/ofertar", method = RequestMethod.GET)
    public ModelAndView ofertarMateriales() {
        return new ModelAndView("ofertar-material");
    }



    /*----------------------------- VISTA MIS PUBLICACIONES -----------------------------*/

    @RequestMapping(value = "/guardarPublicacion", method = RequestMethod.POST)
    public ModelAndView guardarPublicacion(@RequestParam("nombre") String nombre,
                                           @RequestParam("precio") Double precio,
                                           @RequestParam("stock") Integer stock,
                                           HttpServletRequest request) {


        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ID") == null) {

            return new ModelAndView ("redirect:/login");
        }

        Long idUsuario = (Long) request.getSession().getAttribute("ID");
        Publicacion publicacion = new Publicacion();
        publicacion.setNombre(nombre);
        publicacion.setPrecio(precio);
        publicacion.setStock(stock);
        Cliente cliente = servicioCliente.buscarPorId(idUsuario);
        publicacion.setClientePublicacion(cliente);

        servicioPublicacion.guardarPublicacion(publicacion);
        return new ModelAndView("redirect:/misPublicaciones");
    }


    @RequestMapping(value = "/misPublicaciones", method = RequestMethod.GET)
    public ModelAndView mostrarMisPublicaciones(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        Long idUsuario = (Long) session.getAttribute("ID");
        Cliente cliente = servicioCliente.buscarPorId(idUsuario);

        if (cliente == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener las publicaciones del cliente
        List<Publicacion> publicaciones = servicioPublicacion.getListadoPublicacionPorCliente(cliente);

        // Pasar las publicaciones al modelo
        ModelMap model = new ModelMap();
        model.put("publicaciones", publicaciones);

        return new ModelAndView("mis-publicaciones", model);
    }


    //Muestra la lista de todas las publicaciones de los usuarios
    @RequestMapping(value = "/publicaciones", method = RequestMethod.GET)
    public ModelAndView mostrarPublicaciones(
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // Verificar si el usuario está logueado
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener el ID del usuario desde la sesión
        Long idUsuario = (Long) session.getAttribute("ID");

        // Buscar el cliente por su ID usando el servicioCliente
        Cliente cliente = servicioCliente.buscarPorId(idUsuario);

        // Verificar si el cliente existe
        if (cliente == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener las publicaciones del cliente
        List<Publicacion> publicaciones = servicioPublicacion.getListadoPublicacionPorCliente(cliente);

        // Pasar las publicaciones al modelo
        ModelMap model = new ModelMap();
        model.put("publicaciones", publicaciones);

        return new ModelAndView("mis-publicaciones", model);

    }


    @RequestMapping(value = "/eliminar-publicacion/{id}", method = RequestMethod.POST)
    public ModelAndView eliminarPublicacion(@PathVariable Long id) {
        servicioPublicacion.eliminarPublicacion(id);
        return new ModelAndView("redirect:/misPublicaciones");
    }


    @RequestMapping(value = "/buscar-publicaciones", method = RequestMethod.GET)
    public ModelAndView buscarPublicaciones(@RequestParam("search") String nombre, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // Verificar si el usuario está logueado
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener el ID del usuario desde la sesión
        Long idUsuario = (Long) session.getAttribute("ID");


        // Buscar las publicaciones que coincidan con el nombre proporcionado
        List<Publicacion> publicaciones = servicioPublicacion.buscarPublicacionesPorNombre(nombre);

        // Pasar las publicaciones al modelo
        ModelMap model = new ModelMap();
        model.put("publicaciones", publicaciones);

        return new ModelAndView("mis-publicaciones", model);
    }


    @RequestMapping(value = "/limpiar-busqueda", method = RequestMethod.GET)
    public ModelAndView limpiarBusqueda(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // Verificar si el usuario está logueado
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        Long idUsuario = (Long) session.getAttribute("ID");
        Cliente cliente = servicioCliente.buscarPorId(idUsuario);

        // Obtener todas las publicaciones del cliente
        List<Publicacion> publicaciones = servicioPublicacion.getListadoPublicacionPorCliente(cliente);

        // Pasar las publicaciones al modelo
        ModelMap model = new ModelMap();
        model.put("publicaciones", publicaciones);

        return new ModelAndView("mis-publicaciones", model);
    }



    /*----------------------------- VISTA EDITAR PUBLICACIONES -----------------------------*/

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public ModelAndView editarPublicacion(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        Long idUsuario = (Long) session.getAttribute("ID");
        Publicacion publicacion = servicioPublicacion.buscarPublicacionPorId(id);

        // Verifica si la publicación existe y pertenece al usuario
        if (publicacion != null && publicacion.getClientePublicacion().getId().equals(idUsuario)) {
            ModelAndView modelAndView = new ModelAndView("editar-publicacion");
            modelAndView.addObject("publicacion", publicacion); // Agrega la publicación al modelo
            return modelAndView;
        }

        return new ModelAndView("redirect:/misPublicaciones");
    }


    @RequestMapping(value = "/guardar-cambios/{id}", method = RequestMethod.POST)
    public ModelAndView guardarCambios(@PathVariable Long id,
                                       @RequestParam("nombre") String nombre,
                                       @RequestParam("precio") Double precio,
                                       @RequestParam("stock") Integer stock,
                                       HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        Long idUsuario = (Long) session.getAttribute("ID");
        Publicacion publicacion = servicioPublicacion.buscarPublicacionPorId(id);

        if (publicacion != null && publicacion.getClientePublicacion().getId().equals(idUsuario)) {
            // Actualizar los atributos de la publicación
            publicacion.setNombre(nombre);
            publicacion.setPrecio(precio);
            publicacion.setStock(stock);

            servicioPublicacion.modificarPublicacion(publicacion);
        }

        return new ModelAndView("redirect:/misPublicaciones");
    }



    /*----------------------------- VISTA MIS COMPRAS -----------------------------*/

    @GetMapping("/comprar-material")
    public ModelAndView mostrarTodasLasPublicaciones() {
        ModelAndView modelAndView = new ModelAndView("comprar-material"); // Especifica la vista 'comprar-material'
        List<Publicacion> publicaciones = servicioPublicacion.getListadoPublicacion();

        // Agregar la lista de publicaciones al modelo
        modelAndView.addObject("publicaciones", publicaciones);

        return modelAndView; // Retorna el objeto ModelAndView
    }

    @RequestMapping(value = "/ordenar-precio", method = RequestMethod.GET)
    public ModelAndView ordenarPrecio(@RequestParam String ordenar) {
        List<Publicacion> publicaciones;

    // Dependiendo de la opción seleccionada, ordena las publicaciones
        if ("mayor".equals(ordenar)) {
            publicaciones = servicioPublicacion.listarPublicacionesPorMayorPrecio();
        } else if ("menor".equals(ordenar)) {
            publicaciones = servicioPublicacion.listarPublicacionesPorMenorPrecio();
        } else {
            publicaciones = servicioPublicacion.getListadoPublicacion();
        }

        ModelAndView modelAndView = new ModelAndView("comprar-material");
        modelAndView.addObject("publicaciones", publicaciones);
        modelAndView.addObject("ordenar", ordenar); // Agrega la opción seleccionada al modelo

        return modelAndView;
    }

}
