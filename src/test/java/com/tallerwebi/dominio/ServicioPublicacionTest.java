package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PublicacionNoEncontradaException;
import com.tallerwebi.dominio.implementacion.ServicioPublicacionImpl;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioPublicacion;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioPublicacion;
import com.tallerwebi.dominio.modelo.Publicacion;
import com.tallerwebi.infraestructura.RepositorioPublicacionImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServicioPublicacionTest {

    private ServicioPublicacion servicioPublicacion;
    private RepositorioPublicacion repositorioPublicacion;
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    public void setUp() {
        repositorioPublicacion = Mockito.mock(RepositorioPublicacionImpl.class);
        repositorioUsuario = Mockito.mock(RepositorioUsuarioImpl.class);
        servicioPublicacion = new ServicioPublicacionImpl(repositorioPublicacion);

    }

    @Test
    @Transactional
    @Rollback
    public void queSeGuardeUnaPublicacionCorrectamente() {
        Publicacion publicacion = new Publicacion();
        publicacion.setNombre("Publicación de prueba");
        publicacion.setPrecio(100.0);
        publicacion.setStock(10);

        servicioPublicacion.guardarPublicacion(publicacion);

        verify(repositorioPublicacion).guardar(publicacion);
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaListadoDePublicacionesCorrectamenteSiExistenPublicaciones() {
        List<Publicacion> publicacionesMock = new ArrayList<>();
        publicacionesMock.add(crearPublicacion());
        publicacionesMock.add(crearPublicacion());

        when(repositorioPublicacion.listadoPublicacion()).thenReturn(publicacionesMock);

        List<Publicacion> resultado = servicioPublicacion.getListadoPublicacion();

        assertThat(resultado, is(not(empty())));
        assertThat(resultado, hasSize(2));
        verify(repositorioPublicacion).listadoPublicacion();
    }

    @Test
    @Transactional
    @Rollback
    public void queLanceExcepcionCuandoLaPublicacionAEliminarNoExiste() {
        Long publicacionIdInexistente = 999L;
        when(repositorioPublicacion.obtenerPublicacionPorId(publicacionIdInexistente)).thenReturn(null);

        assertThrows(PublicacionNoEncontradaException.class, () -> {
            servicioPublicacion.eliminarPublicacion(publicacionIdInexistente);
        });

        verify(repositorioPublicacion).obtenerPublicacionPorId(publicacionIdInexistente);
    }

    @Test
    @Transactional
    @Rollback
    public void queLanceExcepcionCuandoLaPublicacionAModificarNoExiste() {
        // Arrange
        Publicacion publicacion = new Publicacion();
        publicacion.setId(999L);
        publicacion.setNombre("Nueva publicación");
        publicacion.setPrecio(200.0);
        publicacion.setStock(15);

        when(repositorioPublicacion.obtenerPublicacionPorId(publicacion.getId())).thenReturn(null);

        // Act & Assert
        assertThrows(PublicacionNoEncontradaException.class, () -> {
            servicioPublicacion.modificarPublicacion(publicacion);
        });

        verify(repositorioPublicacion).obtenerPublicacionPorId(publicacion.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void queSiBuscoPublicacionesPorNombreYExistanLasEncuentreCorrectamente() {
        Publicacion publicacion1 = crearPublicacion();
        Publicacion publicacion2 = crearPublicacion();
        //ambas tienen de nombre Publicacion

        List<Publicacion> publicacionesMock = new ArrayList<>();
        publicacionesMock.add(publicacion1);
        publicacionesMock.add(publicacion2);

        when(repositorioPublicacion.buscarPublicacionPorNombre("Publicación")).thenReturn(publicacionesMock);

        // Act
        List<Publicacion> resultado = servicioPublicacion.buscarPublicacionesPorNombre("Publicación");

        // Assert
        assertThat(resultado, is(not(empty())));
        assertThat(resultado, hasSize(2));
        verify(repositorioPublicacion).buscarPublicacionPorNombre("Publicación");
    }


    //METODOS

    private static @NotNull Publicacion crearPublicacion() {
        Publicacion publicacion = new Publicacion();
        publicacion.setNombre("Publicación");
        publicacion.setPrecio(100.0);
        publicacion.setStock(10);
        return publicacion;
    }
}
