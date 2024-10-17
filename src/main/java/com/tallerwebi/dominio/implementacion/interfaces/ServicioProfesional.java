package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Profesional;

import java.util.List;

public interface ServicioProfesional {
    void guardar(Profesional profesional);

    void actualizar(Profesional profesional);

    void eliminar(Profesional profesional);

    void eliminar(Long profesionalId);

    List<Profesional> listar();

    Profesional buscarPorId(Long id);

    Profesional buscarProfesionalPorEmail(String email);
}
