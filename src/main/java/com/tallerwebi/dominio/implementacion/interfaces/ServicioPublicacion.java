package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Publicacion;

import java.util.List;

public interface ServicioPublicacion {

    void guardarPublicacion(Publicacion publicacion);

    List<Publicacion> getListadoPublicacion();

    List<Publicacion> getListadoPublicacionPorCliente(Cliente cliente);

    void eliminarPublicacion(Long id);

    List<Publicacion> buscarPublicacionesPorNombre(String nombre);

    Publicacion buscarPublicacionPorId(Long id);

    void modificarPublicacion(Publicacion publicacion);
}