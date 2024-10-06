package com.tallerwebi.dominio;

import com.tallerwebi.dominio.modelo.Proyecto;

import java.util.List;

public interface ServicioProyecto {
    void guardarProyecto(Proyecto proyecto);

    Proyecto obtenerProyectoPorId(Long idProyecto);

//    List<Proyecto> obtenerProyectosPorEstado(String nuevo);
}
