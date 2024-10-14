package com.tallerwebi.dominio.implementacion;

//import com.tallerwebi.dominio.implementacion.interfaces.RepositorioMaterial;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioPublicacion;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
//import com.tallerwebi.dominio.modelo.Material;
import com.tallerwebi.dominio.modelo.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioPublicacionImpl implements ServicioPublicacion {
    private RepositorioPublicacion repositorioPublicacion;

    @Autowired
    public ServicioPublicacionImpl(RepositorioPublicacion repositorioPublicacion) {this.repositorioPublicacion = repositorioPublicacion;}


    @Override
    public void guardarPublicacion(Publicacion publicacion) {
        this.repositorioPublicacion.guardar(publicacion);
    }



    /*----------------------------- LISTAS -----------------------------*/

    @Override
    public List<Publicacion> getListadoPublicacion(){
        return this.repositorioPublicacion.listadoPublicacion();
    }

    @Override
    public List<Publicacion> getListadoPublicacionPorCliente(Cliente cliente) {
        // Llamar al repositorio para obtener las publicaciones asociadas al cliente
        return repositorioPublicacion.listadoPublicacionPorCliente(cliente);
    }


    /*----------------------------- ELIMINAR -----------------------------*/
    @Override
    public void eliminarPublicacion(Long id) {
        // Busca la publicación por su ID
        Publicacion publicacion = repositorioPublicacion.obtenerPublicacionPorId(id);

        // Verifica si la publicación existe antes de eliminarla
            this.repositorioPublicacion.eliminarPublicacion(publicacion);
    }

    public List<Publicacion> buscarPublicacionesPorNombre(String nombre) {
        return repositorioPublicacion.buscarPublicacionPorNombre(nombre);
    }

}
