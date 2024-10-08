package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.excepcion.MaterialConCantidadNullException;
import com.tallerwebi.dominio.excepcion.MaterialConNombreNullException;
import com.tallerwebi.dominio.excepcion.MaterialConUnidadNullException;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioMaterial;
import com.tallerwebi.dominio.modelo.Material;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioMaterialImpl implements RepositorioMaterial {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMaterialImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Material material) {
        if (material.getNombre() == null) {
            throw new MaterialConNombreNullException("El nombre del material no puede ser nulo");
        }
        if(material.getCantidad()==null){
            throw new MaterialConCantidadNullException("La cantidad del material no puede ser nulo");
        }
        if(material.getUnidad()==null){
            throw new MaterialConUnidadNullException("La unidad del material no puede ser nulo");
        }
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

    @Override
    public Material buscarPorId(Long id) {
        // Busca un material por su ID usando Hibernate
        return sessionFactory.getCurrentSession().get(Material.class, id);
    }

    @Override
    public List<Material> listar() {
        // Lista todos los materiales en la base de datos usando HQL
        return sessionFactory.getCurrentSession().createQuery("FROM Material", Material.class).list();
    }

    @Override
    public void eliminar(Material material) {
        // Elimina un material de la base de datos
        sessionFactory.getCurrentSession().delete(material);
    }

    @Override
    public void actualizar(Material material) {
        // Actualiza un material en la base de datos
        sessionFactory.getCurrentSession().update(material);
    }

    @Override
    public List<Material> filtrarMaterialPorNombre(String nombre) {
        String hql = "FROM Material WHERE nombre = :nombre";
        return this.sessionFactory.getCurrentSession()
                .createQuery(hql, Material.class)
                .setParameter("nombre", "%" + nombre + "%")
                .list();
    }
}