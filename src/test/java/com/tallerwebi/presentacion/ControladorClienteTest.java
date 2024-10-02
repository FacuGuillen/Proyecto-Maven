package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.ServicioCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorClienteTest {

    @Mock
    private ServicioCliente servicioCliente;

    @InjectMocks
    private ControladorCliente controladorCliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
/*
    @Test
    public void testMostrarClientes() {
        List<Cliente> clientesMock = List.of(new Cliente(1L, "Juan Perez", "juan@mail.com", "123456789"));
        when(servicioCliente.listarClientes()).thenReturn(clientesMock);

        ModelAndView modelAndView = controladorCliente.mostrarClientes();

        assertThat(modelAndView.getViewName(), equalTo("lista-clientes"));
        assertThat(((List<Cliente>) modelAndView.getModel().get("clientes")), equalTo(clientesMock));
        verify(servicioCliente, times(1)).listarClientes();
    }

 */

    @Test
    public void testAgregarCliente() {
        ModelAndView modelAndView = controladorCliente.agregarCliente();

        assertThat(modelAndView.getViewName(), equalTo("agregar-cliente"));
    }

    @Test
    public void testGuardarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana Gomez");
        cliente.setEmail("ana@mail.com");
        cliente.setTelefono("987654321");

        controladorCliente.guardarCliente(cliente);

        verify(servicioCliente, times(1)).guardarCliente(cliente);
    }
/*
    @Test
    public void testEditarCliente() {
        Long clienteId = 1L;
        Cliente clienteMock = new Cliente(clienteId, "Carlos Ruiz", "carlos@mail.com", "123456789");
        when(servicioCliente.buscarClientePorId(clienteId)).thenReturn(clienteMock);

        ModelAndView modelAndView = controladorCliente.editarCliente(clienteId);

        assertThat(modelAndView.getViewName(), equalTo("editar-cliente"));
        assertThat(modelAndView.getModel().get("cliente"), equalTo(clienteMock));
        verify(servicioCliente, times(1)).buscarClientePorId(clienteId);
    }
*/
    @Test
    public void testEliminarCliente() {
        Long clienteId = 1L;

        controladorCliente.eliminarCliente(clienteId);

        verify(servicioCliente, times(1)).eliminarCliente(clienteId);
    }

    @Test
    public void testActualizarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Updated Name");
        cliente.setEmail("updated@mail.com");
        cliente.setTelefono("123456789");

        controladorCliente.actualizarCliente(cliente);

        verify(servicioCliente, times(1)).actualizarCliente(cliente);
    }
}