package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.repositorio.RepositorioMaterial;
import com.tallerwebi.dominio.modelo.Material;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioMaterialImpl implements RepositorioMaterial {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMaterialImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Material material) {
        this.sessionFactory.getCurrentSession().save(material);
    }

    @Override
    public Material buscarPorNombre(String nombre) {
        String hql = "FROM Material WHERE nombre = :nombre";
        return (Material) this.sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("nombre", nombre)
                .uniqueResult();
    }
}

// Commit //