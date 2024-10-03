package com.tallerwebi.dominio;

import com.tallerwebi.dominio.modelo.Material;
import java.util.List;

public interface ServicioMaterial {
    void guardarMaterial(Material material);
    Material buscarMaterialPorId(Long id);
    List<Material> listarMateriales();
    void eliminarMaterial(Long id);
    void actualizarMaterial(Material material);

}