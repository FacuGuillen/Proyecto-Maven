package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Proyecto;
import com.tallerwebi.dominio.repositorio.RepositorioProyecto;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioProyectoImpl implements RepositorioProyecto {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioProyectoImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public void guardar(Proyecto proyecto) {
        this.sessionFactory.getCurrentSession().save(proyecto);
    }

    @Override
    public List<Proyecto> obtener() {
        String hql = "From Proyecto";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Proyecto obtenerPorId(Long idProyecto) {
        return this.sessionFactory.getCurrentSession().get(Proyecto.class, idProyecto);
    }

//    @Override
//    public void actualizar(Proyecto proyecto) {
//        String hql = "UPDATE Proyecto SET descripcion = :descripcion WHERE id = :id";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("descripcion", proyecto.getDescripcion());
//        query.setParameter("id", proyecto.getId());
//        query.executeUpdate();
//    }

//    @Override
//    public List<Proyecto> obtenerProyectosPorEstado(String nombreEstado) {
//        String hql = "FROM Proyecto p WHERE p.estado.nombre = :estadoNombre";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("estadoNombre", nombreEstado);
//        return query.getResultList();
//    }
//
//    @Override
//    public void actualizar(Proyecto proyecto) {
//        this.sessionFactory.getCurrentSession().merge(proyecto);
//    }

}
