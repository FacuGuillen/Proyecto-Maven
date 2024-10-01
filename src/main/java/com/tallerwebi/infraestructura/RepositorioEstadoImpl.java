package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Estado;
import com.tallerwebi.dominio.repositorio.RepositorioEstado;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class RepositorioEstadoImpl implements RepositorioEstado {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEstadoImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public void save(Estado estadoNuevo) {
        this.sessionFactory.getCurrentSession().save(estadoNuevo);
    }

    @Override
    public Estado obtenerEstadoPorNombre(String nombre) {
        String hql = "FROM Estado WHERE nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", nombre);
        return (Estado) query.uniqueResult();
    }
}
