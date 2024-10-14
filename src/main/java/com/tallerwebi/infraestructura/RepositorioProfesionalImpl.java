package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioProfesional;
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

    @Override
    public Profesional buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Profesional.class, id);
    }


    @Override
    public void actualizar(Profesional cliente) {
        sessionFactory.getCurrentSession().update(cliente);
    }

    @Override
    public Profesional buscarPorEmail(String email) {
        String hql = "FROM Profesional WHERE email = :email";
        return (Profesional) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public List<Profesional> listar() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Profesional", Profesional.class)
                .getResultList();
    }


}
