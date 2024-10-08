package com.tallerwebi.dominio.implementacion.interfaces;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Publicacion;

import java.util.List;

public interface RepositorioPublicacion {
    void guardar(Publicacion publicacion);
    List<Publicacion> listarPublicacionPorCliente(Cliente cliente);

}
