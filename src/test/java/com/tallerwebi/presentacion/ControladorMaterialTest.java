package com.tallerwebi.presentacion;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tallerwebi.dominio.ServicioMaterial;
import com.tallerwebi.dominio.modelo.Material;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ControladorMaterialTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ServicioMaterial servicioMaterial;

    @InjectMocks
    private ControladorMaterial controladorMaterial;

    @Test
    public void dadoQueSeRealizaUnaSolicitudParaAgregarMaterialSobranteEntoncesEsProcesadaCorrectamente() throws Exception {
        Material material = new Material();
        material.setNombre("Yeso");
        material.setCantidad(15.0);
        material.setSobrante(true);

        mockMvc.perform(post("/materiales/guardar-sobrante")
                        .param("nombre", material.getNombre())
                        .param("cantidad", String.valueOf(material.getCantidad()))
                        .param("sobrante", "true"))
                .andExpect(status().isOk());

        verify(servicioMaterial, times(1)).agregarMaterialSobrante(any(Material.class));
    }

    @Test
    public void dadoQueSolicitoMaterialesSobrantesEntoncesLosObtengoEnLaVista() throws Exception {
        Material material1 = new Material();
        material1.setNombre("Madera");
        material1.setCantidad(7.0);
        material1.setSobrante(true);

        when(servicioMaterial.listarMaterialesSobrantes()).thenReturn(List.of(material1));

        mockMvc.perform(get("/materiales/sobrantes"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("materialesSobrantes"))
                .andExpect(view().name("lista-materiales-sobrantes"));
    }
}