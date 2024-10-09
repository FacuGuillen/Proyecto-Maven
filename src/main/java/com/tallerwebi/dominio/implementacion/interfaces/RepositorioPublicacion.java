package com.tallerwebi.dominio.implementacion.interfaces;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Publicacion;

import java.util.List;

public interface RepositorioPublicacion {
    void guardar(Publicacion publicacion);

    void eliminar(Publicacion publicacion);

    List<Publicacion> listadoPublicacion();

    List<Publicacion> listadoPublicacionPorCliente(Cliente cliente);
}
