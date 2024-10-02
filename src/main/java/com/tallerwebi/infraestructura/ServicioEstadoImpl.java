package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioEstado;
import com.tallerwebi.dominio.modelo.Estado;
import com.tallerwebi.dominio.repositorio.RepositorioEstado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioEstado")
@Transactional
public class ServicioEstadoImpl implements ServicioEstado{
    private RepositorioEstado repositorioEstado;

    @Autowired
    public ServicioEstadoImpl(RepositorioEstado repositorioEstado){
        this.repositorioEstado = repositorioEstado;
    }

    @Override
    public void save(Estado estadoNuevo) {
        this.repositorioEstado.save(estadoNuevo);
    }

    @Override
    public Estado obtenerEstadoPorNombre(String nuevo) {
        return this.repositorioEstado.obtenerEstadoPorNombre(nuevo);
    }
}
