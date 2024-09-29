package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Proyecto;

import java.util.List;

public interface RepositorioProyecto {
    void guardar(Proyecto proyecto);

    List<Proyecto> obtener();

    void actualizar(Proyecto proyecto);
}
