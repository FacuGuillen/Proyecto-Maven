package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.repositorio.RepositorioCliente;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}

// Commit //