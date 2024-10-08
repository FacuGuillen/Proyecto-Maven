package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.implementacion.interfaces.RepositorioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Publicacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPublicacionImpl implements RepositorioPublicacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPublicacionImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public void guardar(Publicacion publicacion) {
//        if (publicacion.getNombre() == null || publicacion.getStock() == null || publicacion.getStock() == 0 || publicacion.getPrecio() == null || publicacion.getPublicacionPausada() == null){
//            return;
//        }
        this.sessionFactory.getCurrentSession().save(publicacion);
    }

    @Override
    public List<Publicacion> listarPublicacionPorCliente(Cliente cliente) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Publicacion p WHERE p.clientePublicacion = :cliente", Publicacion.class)
                .setParameter("cliente", cliente)
                .list();
    }

    @Override
    public void eliminar(Publicacion publicacion) {
        this.sessionFactory.getCurrentSession().delete(publicacion);
    }
}
