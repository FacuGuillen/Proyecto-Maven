package com.tallerwebi.dominio;

public interface RepositorioMaterial {
    void guardar(Material material);
    Material buscarPorNombre(String nombre);
}

//