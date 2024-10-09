package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioConsulta;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.implementacion.ServicioConsultaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioConsultaImplTest {
    private ServicioConsulta servicioConsulta;
    private RepositorioConsulta repositorioConsulta;
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    public void init() {
        // Inicializar los mocks primero
        this.repositorioConsulta = mock(RepositorioConsulta.class);
        this.repositorioUsuario = mock(RepositorioUsuario.class);
        this.servicioConsulta = new ServicioConsultaImpl(this.repositorioConsulta, this.repositorioUsuario);
    }
    @Test
    void agregarConsultaDeberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        Long idUsuarioInexistente = 1L;
        Consulta consulta = new Consulta();
        when(repositorioUsuario.findById(idUsuarioInexistente)).thenReturn(null);

        // Ejecución y Verificación

        assertThrows(UsuarioNoEncontradoException.class, () -> {
            servicioConsulta.agregarConsulta(idUsuarioInexistente, consulta);
        });
    }

    @Test
    void agregarConsultaDeberiaGuardarConsultaCuandoUsuarioEsCliente() throws UsuarioNoEncontradoException, UsuarioSinPermisosException {
        Long idClienteExistente = 1L;
        Consulta consulta = new Consulta();
        Cliente clienteExistente = new Cliente();
        when(repositorioUsuario.findById(idClienteExistente)).thenReturn(clienteExistente);

        // Ejecución
        servicioConsulta.agregarConsulta(idClienteExistente, consulta);

        // Verificación
        verify(repositorioConsulta).save(consulta);
        assertThat(consulta.getUsuario(), is(clienteExistente));
    }
    @Test
    void listarConsultasDeberiaRetornarConsultasCuandoUsuarioExiste()  throws UsuarioNoEncontradoException {
        Long idUsuarioExistente = 1L;
        Usuario usuarioExistente = new Usuario();
        List<Consulta> listaConsultas = Arrays.asList(new Consulta(), new Consulta());
        when(repositorioUsuario.findById(idUsuarioExistente)).thenReturn(usuarioExistente);
        when(repositorioConsulta.obtenerConsultasByUsuario(usuarioExistente)).thenReturn(listaConsultas);

        // Ejecución
        List<Consulta> consultasObtenidas = servicioConsulta.listarConsultasByIdUsuario(idUsuarioExistente);

        // Verificación
        assertThat(consultasObtenidas, is(not(empty())));
        assertThat(consultasObtenidas.size(), is(2));
    }
    @Test
    void listarConsultasDeberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        Long idUsuarioInexistente = 1L;
        when(repositorioUsuario.findById(idUsuarioInexistente)).thenReturn(null);

        // Ejecución y Verificación
        assertThrows(UsuarioNoEncontradoException.class, () -> {
            servicioConsulta.listarConsultasByIdUsuario(idUsuarioInexistente);
        });
    }
}
