package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioConEmailNullException;
import com.tallerwebi.dominio.excepcion.UsuarioConNombreNullException;
import com.tallerwebi.dominio.excepcion.UsuarioConPasswordNullException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioProfesional;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioProfesional;
import com.tallerwebi.dominio.implementacion.ServicioProfesionalImpl;
import com.tallerwebi.dominio.modelo.Profesional;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioProfesionalTest {

    private ServicioProfesional servicioProfesional;
    private RepositorioProfesional repositorioProfesional;

    @BeforeEach
    public void setUp() {
        repositorioProfesional = Mockito.mock(RepositorioProfesional.class);
        servicioProfesional = new ServicioProfesionalImpl(repositorioProfesional);
    }

    @Test
    public void dadoQueGuardoUnProfesionalEntoncesSeInvocaElMetodoGuardarDelRepositorio() {
        Profesional profesional = new Profesional();
        profesional.setNombre("Juan");
        profesional.setEmail("juan@mail.com");
        profesional.setTelefono("123456789");
        profesional.setPassword("123");

        servicioProfesional.guardar(profesional);

        verify(repositorioProfesional, times(1)).guardar(profesional);
    }

    @Test
    public void dadoQueBuscoUnProfesionalPorEmailEntoncesDevuelveElProfesionalCorrecto() {
        Profesional profesional = new Profesional();
        profesional.setEmail("juan@mail.com");

        when(repositorioProfesional.buscarPorEmail("juan@mail.com")).thenReturn(profesional);

        Profesional resultado = servicioProfesional.buscarProfesionalPorEmail("juan@mail.com");
        assertThat(resultado, equalTo(profesional));
    }

    @Test
    public void dadoQueListoProfesionalesEntoncesDevuelveLaListaCompleta() {
        Profesional profesional1 = new Profesional();
        profesional1.setNombre("Juan");
        Profesional profesional2 = new Profesional();
        profesional2.setNombre("Ana");

        List<Profesional> listaProfesionales = Arrays.asList(profesional1, profesional2);
        when(repositorioProfesional.listar()).thenReturn(listaProfesionales);

        List<Profesional> resultado = servicioProfesional.listar();
        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0).getNombre(), equalTo("Juan"));
    }

    @Test
    public void dadoQueEliminoUnProfesionalEntoncesElRepositorioLoElimina() {
        Profesional profesional = new Profesional();

        servicioProfesional.eliminar(profesional);
        verify(repositorioProfesional, times(1)).eliminar(profesional);
    }

    @Test
    public void dadoQueTratoDeEliminarUnProfesionalEnNullEntoncesArrojoUnaUsuarioInexistenteException() {
        Profesional profesional = null;

        assertThrows(UsuarioInexistenteException.class, () ->{
            servicioProfesional.eliminar(profesional);
        });


    }

    @Test
    @Transactional
    public void dadoQueIntentoGuardarUnProfesionalConNombreNuloEntoncesArrojaLaExcepcionUsuarioConNombreNullException() {
        // Arrange
        Profesional profesional = crearProfesionalConNombreNullParaQueLanceLaUsuarioConNombreNullException();

        // Act & Assert
        assertThrows(UsuarioConNombreNullException.class, () -> {
            this.servicioProfesional.guardar(profesional); // Llamar al método del servicio
        });
    }

    @Test
    @Transactional
    public void dadoQueIntentoGuardarUnProfesionalConEmailNuloEntoncesEntoncesArrojaLaExcepcionUsuarioConEmailNullException() {
        Profesional profesional = crearUnProfesionalConEmailNullParaQueLanceLaUsuarioConEmailNullException();

        assertThrows(UsuarioConEmailNullException.class, () -> {
            this.servicioProfesional.guardar(profesional);
        });
    }

    @Test
    @Transactional
    public void dadoQueIntentoGuardarUnProfesionalConPasswordNuloEntoncesArrojaLaExcepcionUsuarioConPasswordNullException() {
        Profesional profesional = crearUnProfesionalConPasswordNullParaQueLanceLaUsuarioConPasswordNullException();

        assertThrows(UsuarioConPasswordNullException.class, () -> {
            this.servicioProfesional.guardar(profesional);
        });
    }

    // METODOS

    private static @NotNull Profesional crearProfesionalConNombreNullParaQueLanceLaUsuarioConNombreNullException() {
        Profesional profesional = new Profesional();
        profesional.setNombre(null);
        profesional.setEmail("sinNombre@mail.com");
        profesional.setTelefono("111222333");
        profesional.setPassword("password123");
        return profesional;
    }

    private static @NotNull Profesional crearUnProfesionalConEmailNullParaQueLanceLaUsuarioConEmailNullException() {
        Profesional profesional = new Profesional();
        profesional.setNombre("Juan Carlos");
        profesional.setEmail(null);  // Email nulo
        profesional.setTelefono("123456789");
        profesional.setPassword("password123");
        return profesional;
    }

    private static @NotNull Profesional crearUnProfesionalConPasswordNullParaQueLanceLaUsuarioConPasswordNullException() {
        Profesional profesional = new Profesional();
        profesional.setNombre("Juan Carlos");
        profesional.setEmail("sinNombre@mail.com");  // Email nulo
        profesional.setTelefono("123456789");
        profesional.setPassword(null);
        return profesional;
    }
}
