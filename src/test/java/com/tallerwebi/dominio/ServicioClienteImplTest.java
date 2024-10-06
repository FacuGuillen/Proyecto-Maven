package com.tallerwebi.dominio;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.repositorio.RepositorioCliente;
import com.tallerwebi.infraestructura.ServicioClienteImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioClienteImplTest {

    private ServicioCliente servicioCliente;
    private RepositorioCliente repositorioCliente;

    @BeforeEach
    public void setUp() {
        repositorioCliente = Mockito.mock(RepositorioCliente.class);
        servicioCliente = new ServicioClienteImpl(repositorioCliente);
    }

    @Test
    public void dadoQueGuardoUnClienteEntoncesSeInvocaElMetodoGuardarDelRepositorio() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setEmail("juan@mail.com");
        cliente.setTelefono("123456789");

        servicioCliente.guardarCliente(cliente);

        verify(repositorioCliente, times(1)).guardar(cliente);
    }

    @Test
    public void dadoQueBuscoUnClientePorEmailEntoncesDevuelveElClienteCorrecto() {
        Cliente cliente = new Cliente();
        cliente.setEmail("juan@mail.com");

        when(repositorioCliente.buscarPorEmail("juan@mail.com")).thenReturn(cliente);

        Cliente resultado = servicioCliente.buscarClientePorEmail("juan@mail.com");
        assertThat(resultado, equalTo(cliente));
    }

    @Test
    public void dadoQueListoClientesEntoncesDevuelveLaListaCompleta() {
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Juan");
        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Ana");

        List<Cliente> listaClientes = Arrays.asList(cliente1, cliente2);
        when(repositorioCliente.listar()).thenReturn(listaClientes);

        List<Cliente> resultado = servicioCliente.listarClientes();
        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0).getNombre(), equalTo("Juan"));
    }

    @Test
    public void dadoQueEliminoUnClienteEntoncesElRepositorioLoElimina() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(repositorioCliente.buscarPorId(1L)).thenReturn(cliente);

        servicioCliente.eliminarCliente(1L);
        verify(repositorioCliente, times(1)).eliminar(cliente);
    }

    @Test
    public void dadoQueEliminoUnClienteInexistenteEntoncesNoHaceNada() {
        when(repositorioCliente.buscarPorId(2L)).thenReturn(null);

        servicioCliente.eliminarCliente(2L);
        verify(repositorioCliente, never()).eliminar(any());
    }

}