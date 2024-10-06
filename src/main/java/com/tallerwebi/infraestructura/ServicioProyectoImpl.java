package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioProyecto;
import com.tallerwebi.dominio.modelo.Proyecto;
import com.tallerwebi.dominio.modelo.enums.EstadoProyecto;
import com.tallerwebi.dominio.repositorio.RepositorioProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

//    @Override
//    public List<Proyecto> obtenerProyectosPorEstado(String nuevo) {
//        return repositorioProyecto.obtenerProyectosPorEstado(nuevo);
//    }

//    //Metodo del Scheduler
//    @Scheduled(fixedRate = 86400000) // Cada 24 horas
//    public void actualizarEstadoProyectos() {
//        List<Proyecto> proyectos = repositorioProyecto.obtener();
//        LocalDate hoy = LocalDate.now();
//
//        for (Proyecto proyecto : proyectos) {
//            if (proyecto.getEstadoProyecto() == EstadoProyecto.POR_INICIAR &&
//                    proyecto.getFechaInicioProyecto().isEqual(hoy)) {
//                proyecto.setEstadoProyecto(EstadoProyecto.INICIADO);
//                repositorioProyecto.actualizar(proyecto);
//            }
//        }
//    }


}
