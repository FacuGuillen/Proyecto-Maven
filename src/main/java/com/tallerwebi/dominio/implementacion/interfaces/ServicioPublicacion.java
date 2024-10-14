package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Publicacion;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ServicioPublicacion {

    void guardarPublicacion(Publicacion publicacion);

    List<Publicacion> getListadoPublicacion();

    List<Publicacion> getListadoPublicacionPorCliente(Cliente cliente);

    void eliminarPublicacion(Long id);


}
