package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Profesional;

import java.util.List;

public interface RepositorioProfesional {
    void guardar(Profesional profesional);

    List<Profesional> obtener();

    void eliminar(Profesional profesional);

    void actualizar(Profesional cliente);

    Profesional buscarPorEmail(String email);

    List<Profesional> listar();

    Profesional buscarPorId(Long id);
}
