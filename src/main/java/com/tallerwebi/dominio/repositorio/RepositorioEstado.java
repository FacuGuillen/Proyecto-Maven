package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Estado;

public interface RepositorioEstado {
    void save(Estado estadoNuevo);

    Estado obtenerEstadoPorNombre(String nuevo);
}
