package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Profesional;

import java.util.List;

public interface RepositorioProfesional {
    void guardar(Profesional profesional);

    List<Profesional> obtener();

    void eliminar(Profesional profesional);
}
