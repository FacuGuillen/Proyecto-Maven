package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.implementacion.interfaces.ServicioProyecto;
import com.tallerwebi.dominio.modelo.Proyecto;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("servicioProyecto")
@Transactional
public class ServicioProyectoImpl implements ServicioProyecto {

    private RepositorioProyecto repositorioProyecto;

    @Autowired
    public ServicioProyectoImpl(RepositorioProyecto repositorioProyecto){
        this.repositorioProyecto = repositorioProyecto;
    }


    @Override
    public void guardarProyecto(Proyecto proyecto) {
        repositorioProyecto.guardar(proyecto);
    }

    @Override
    public Proyecto obtenerProyectoPorId(Long idProyecto) {
        return repositorioProyecto.obtenerPorId(idProyecto);
    }



}
