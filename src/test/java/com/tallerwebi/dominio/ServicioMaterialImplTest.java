package com.tallerwebi.dominio;

import com.tallerwebi.dominio.modelo.Material;
import com.tallerwebi.dominio.repositorio.RepositorioMaterial;
import com.tallerwebi.infraestructura.ServicioMaterialImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioMaterialImplTest {

    private ServicioMaterial servicioMaterial;
    private RepositorioMaterial repositorioMaterial;

    @BeforeEach
    public void setUp() {
        repositorioMaterial = Mockito.mock(RepositorioMaterial.class);
        servicioMaterial = new ServicioMaterialImpl(repositorioMaterial);
    }

    @Test
    public void dadoQueGuardoUnMaterialEntoncesSeAlmacenaCorrectamente() {
        Material material = new Material();
        material.setNombre("Cemento");

        servicioMaterial.guardarMaterial(material);

        verify(repositorioMaterial, times(1)).guardar(material);
    }

    @Test
    public void dadoQueBuscoUnMaterialPorIdEntoncesLoEncuentro() {
        Material material = new Material();
        material.setId(1L);
        material.setNombre("Cemento");

        when(repositorioMaterial.buscarPorId(1L)).thenReturn(material);

        Material materialObtenido = servicioMaterial.buscarMaterialPorId(1L);

        assertThat(materialObtenido, equalTo(material));
    }

    @Test
    public void dadoQueListoMaterialesEntoncesObtengoTodosLosMateriales() {
        Material material1 = new Material();
        material1.setNombre("Cemento");

        Material material2 = new Material();
        material2.setNombre("Arena");

        when(repositorioMaterial.listar()).thenReturn(List.of(material1, material2));

        List<Material> materiales = servicioMaterial.listarMateriales();

        assertThat(materiales.size(), equalTo(2));
    }

    @Test
    public void dadoQueEliminoUnMaterialEntoncesEsRemovidoDelRepositorio() {
        Material material = new Material();
        material.setId(1L);

        when(repositorioMaterial.buscarPorId(1L)).thenReturn(material);

        servicioMaterial.eliminarMaterial(1L);

        verify(repositorioMaterial, times(1)).eliminar(material);
    }

    @Test
    public void dadoQueActualizoUnMaterialEntoncesLosCambiosSeReflejan() {
        Material material = new Material();
        material.setId(1L);
        material.setNombre("Cemento Actualizado");

        servicioMaterial.actualizarMaterial(material);

        verify(repositorioMaterial, times(1)).actualizar(material);
    }
}
