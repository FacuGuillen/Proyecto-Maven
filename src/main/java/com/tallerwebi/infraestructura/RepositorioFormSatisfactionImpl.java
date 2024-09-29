package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.FormSatisfaction;
import com.tallerwebi.dominio.repositorio.RepositorioFormSatisfaction;
import org.hibernate.SessionFactory;

public class RepositorioFormSatisfactionImpl implements RepositorioFormSatisfaction {
    private SessionFactory sessionFactory;

    public RepositorioFormSatisfactionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(FormSatisfaction formSatisfaction) {
        this.sessionFactory.getCurrentSession().save(formSatisfaction);
    }
}