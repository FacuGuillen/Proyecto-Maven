package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioCliente;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioClienteImpl implements RepositorioCliente {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioClienteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Cliente cliente) {
        this.sessionFactory.getCurrentSession().save(cliente);
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        String hql = "FROM Cliente WHERE email = :email";
        return (Cliente) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public List<Cliente> listar() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Cliente", Cliente.class)
                .getResultList();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Cliente.class, id);
    }

    @Override
    public void eliminar(Cliente cliente) {
        sessionFactory.getCurrentSession().delete(cliente);
    }

    @Override
    public void actualizar(Cliente cliente) {
        sessionFactory.getCurrentSession().update(cliente);
    }
}

// Commit //