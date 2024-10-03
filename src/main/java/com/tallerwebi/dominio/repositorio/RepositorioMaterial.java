package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Material;

import java.util.List;

public interface RepositorioMaterial {
    void guardar(Material material);
    Material buscarPorNombre(String nombre);

    Material buscarPorId(Long id);

    List<Material> listar();

    void eliminar(Material material);

    void actualizar(Material material);

    List<Material> filtrarMaterialPorNombre(String nombre);
}
