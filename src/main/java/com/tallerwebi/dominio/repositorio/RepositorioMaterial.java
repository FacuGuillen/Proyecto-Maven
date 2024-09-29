package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Material;

public interface RepositorioMaterial {
    void guardar(Material material);
    Material buscarPorNombre(String nombre);
}
