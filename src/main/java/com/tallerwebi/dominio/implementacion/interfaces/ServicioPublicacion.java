package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Publicacion;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ServicioPublicacion {
    void guardarPublicacion(Publicacion publicacion);
}
