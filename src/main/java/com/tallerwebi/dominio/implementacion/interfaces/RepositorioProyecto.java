package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Proyecto;

import java.util.List;

public interface RepositorioProyecto {
    void guardar(Proyecto proyecto);

    List<Proyecto> obtener();

    Proyecto obtenerPorId(Long idProyecto);
}
