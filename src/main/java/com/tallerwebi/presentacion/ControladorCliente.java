package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        servicioCliente.guardarCliente(cliente);

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
        servicioCliente.guardarCliente(cliente);
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
}

