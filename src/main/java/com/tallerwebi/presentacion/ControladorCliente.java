package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioCliente;
import com.tallerwebi.dominio.modelo.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorCliente {

    private ServicioCliente servicioCliente;

    @Autowired
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @RequestMapping(value = "/nuevo-cliente", method = RequestMethod.GET)
    public ModelAndView crearCliente() {
        return new ModelAndView("nuevo-cliente");
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public ModelAndView mostrarClientes() {
        List<Cliente> clientes = servicioCliente.listarClientes();
        ModelMap modelo = new ModelMap();
        modelo.put("clientes", clientes);
        return new ModelAndView("lista-clientes", modelo);
    }

    @RequestMapping(value = "/registrar-cliente", method = RequestMethod.POST)
    public ModelAndView registrarCliente(@RequestParam("nombre") String nombre,
                                         @RequestParam("email") String email,
                                         @RequestParam("telefono") String telefono) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        servicioCliente.guardar(cliente);

        return new ModelAndView("redirect:/clientes");
    }

    @RequestMapping(value = "/actualizar-cliente", method = RequestMethod.POST)
    public ModelAndView actualizarCliente(@RequestParam("id") Long id,
                                          @RequestParam("nombre") String nombre,
                                          @RequestParam("email") String email,
                                          @RequestParam("telefono") String telefono) {
        Cliente cliente = servicioCliente.buscarClientePorId(id);
        if (cliente != null) {
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            servicioCliente.actualizarCliente(cliente);
        }
        return new ModelAndView("redirect:/clientes");
    }

    @RequestMapping(value = "/eliminar-cliente", method = RequestMethod.POST)
    public ModelAndView eliminarCliente(@RequestParam("id") Long id) {
        servicioCliente.eliminarCliente(id);
        return new ModelAndView("redirect:/clientes");
    }

    @RequestMapping(value = "/agregar-cliente", method = RequestMethod.GET)
    public ModelAndView agregarCliente() {
        return new ModelAndView("agregar-cliente");
    }

    @RequestMapping(value = "/guardar-cliente", method = RequestMethod.POST)
    public void guardarCliente(Cliente cliente) {
        servicioCliente.guardar(cliente);
    }

    @RequestMapping(value = "/editar-cliente", method = RequestMethod.GET)
    public ModelAndView editarCliente(@RequestParam("id") Long clienteId) {
        Cliente cliente = servicioCliente.buscarClientePorId(clienteId);
        ModelAndView modelAndView = new ModelAndView("editar-cliente");
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @RequestMapping(value = "/actualizar-cliente-objeto", method = RequestMethod.POST)
    public void actualizarCliente(Cliente cliente) {
        servicioCliente.actualizarCliente(cliente);
}

    @RequestMapping(value = "/mis-datos", method = RequestMethod.GET)
    public ModelAndView misDatos(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener el ID del cliente de la sesión
        Long clienteId = (Long) session.getAttribute("ID");

        // Buscar el cliente en la base de datos
        Cliente cliente = servicioCliente.buscarClientePorId(clienteId);

        // Si no se encuentra el cliente, redirigir a la página de error o login
        if (cliente == null) {
            return new ModelAndView("redirect:/login");
        }

        // Pasar los datos del cliente a la vista
        ModelAndView modelAndView = new ModelAndView("mis-datos");
        modelAndView.addObject("usuario", cliente);

        return modelAndView;
    }

    @RequestMapping(value = "/actualizas-cliente/{id}", method = RequestMethod.POST)
    public ModelAndView guardarCambiosDatos(@PathVariable Long id,
                                            @RequestParam("email") String email,
                                            @RequestParam("telefono") String telefono, // Cambiado a String para coincidir con el input HTML
                                            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ID") == null) {
            return new ModelAndView("redirect:/login");
        }

        // Obtener el ID del cliente de la sesión
        Long clienteId = (Long) session.getAttribute("ID");

        // Buscar el cliente en la base de datos
        Cliente cliente = servicioCliente.buscarClientePorId(clienteId);

        if (cliente != null && cliente.getId().equals(id)) { // Asegúrate de usar el ID correcto
            // Actualizar los atributos del cliente
            cliente.setEmail(email);
            cliente.setTelefono(telefono);

            // Llamar al servicio para guardar los cambios
            servicioCliente.modificarCliente(cliente);
        }

        return new ModelAndView("redirect:/mis-datos"); // Redirigir a mis-datos
    }




}

