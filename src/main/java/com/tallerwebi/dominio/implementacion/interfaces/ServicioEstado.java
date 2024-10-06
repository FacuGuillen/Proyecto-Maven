package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Estado;

public interface ServicioEstado {
    void save(Estado estadoNuevo);

    Estado obtenerEstadoPorNombre(String nuevo);
}
