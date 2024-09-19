package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.repositorio.RepositorioProfesional;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class RepositorioProfesionalImpl implements RepositorioProfesional {
    private SessionFactory sessionFactory;

    public RepositorioProfesionalImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Profesional profesional) {
        this.sessionFactory.getCurrentSession().save(profesional);
    }

    @Override
    public List<Profesional> obtener() {
        String hql = "FROM Profesional ";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);

        return query.getResultList();
    }

    @Override
    public void eliminar(Profesional profesional) {
        this.sessionFactory.getCurrentSession().delete(profesional);
    }
}
