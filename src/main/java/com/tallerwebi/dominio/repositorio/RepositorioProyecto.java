package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Proyecto;

import java.util.List;

public interface RepositorioProyecto {
    void guardar(Proyecto proyecto);

    List<Proyecto> obtener();

    Proyecto obtenerPorId(Long idProyecto);

//    void actualizar(Proyecto proyecto);
//    List<Proyecto> obtenerProyectosPorEstado(String nombreEstado);
}
