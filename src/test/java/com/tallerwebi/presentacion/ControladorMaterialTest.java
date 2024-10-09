package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioMaterial;
import com.tallerwebi.dominio.modelo.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


public class ControladorMaterialTest {

    @Mock
    private ServicioMaterial servicioMaterial;

    @InjectMocks
    private ControladorMaterial controladorMaterial;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearMaterial() {
        ModelAndView modelAndView = controladorMaterial.crearMaterial();
        assertThat(modelAndView.getViewName(), equalTo("nuevo-material"));
    }

//    @Test
//    public void testGuardarMaterial() {
//        Material material = new Material();
//        material.setNombre("Cemento");
//        material.setCantidad(50.0);
//        material.setUnidad("kg");
//
//        controladorMaterial.guardarMaterial(material);
//        verify(servicioMaterial, times(1)).guardarMaterial(material);
//    }

    @Test
    public void testEliminarMaterial() {
        Long materialId = 1L;

        controladorMaterial.eliminarMaterial(materialId);

        verify(servicioMaterial, times(1)).eliminarMaterial(materialId);
    }

    @Test
    public void testModificarMaterial() {
        Long materialId = 1L;

        Material materialMock = new Material();
        materialMock.setNombre("Cemento");
        materialMock.setCantidad(50.0);
        materialMock.setUnidad("kg");

        when(servicioMaterial.buscarMaterialPorId(materialId)).thenReturn(materialMock);

        ModelAndView modelAndView = controladorMaterial.actualizarMaterial(materialId,"Cemento",100.0,"kg", 100.50);

        assertThat(modelAndView.getViewName(), equalTo("actualizar-material"));
        assertThat(modelAndView.getModel().get("material"), equalTo(materialMock));
        verify(servicioMaterial, times(1)).actualizarMaterial(materialMock);
    }

    @Test
    public void testMostrarMateriales() {

        Material materialMock1 = new Material();
        materialMock1.setNombre("Cemento");
        materialMock1.setCantidad(50.0);
        materialMock1.setUnidad("kg");
        Material materialMock2 = new Material();
        materialMock2.setNombre("Arena");
        materialMock2.setCantidad(30.0);
        materialMock2.setUnidad("kg");

        List<Material> listaMaterialesMock = List.of(materialMock1, materialMock2);

        when(servicioMaterial.listarMateriales()).thenReturn(listaMaterialesMock);


        ModelAndView modelAndView = controladorMaterial.mostrarMisMateriales();

        assertThat(modelAndView.getViewName(), equalTo("mis-materiales"));
        assertThat(modelAndView.getModel().get("materiales"), equalTo(listaMaterialesMock));
        verify(servicioMaterial, times(1)).listarMateriales();
    }

}
