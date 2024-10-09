package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioCliente;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Publicacion;
import com.tallerwebi.dominio.modelo.enums.TipoConsulta;
import com.tallerwebi.dominio.modelo.enums.TipoTrabajo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

        servicioPublicacion.guardarPublicacion(publicacion); // Asumiendo que este es el servicio correcto para guardar la publicación.
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


}
