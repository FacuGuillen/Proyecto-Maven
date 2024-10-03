package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.repositorio.RepositorioConsulta;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioConsultaImpl implements RepositorioConsulta {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioConsultaImpl( SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;

    }

    @Override
    public void save(Consulta consulta) {
    this.sessionFactory.getCurrentSession().save(consulta);
    }

    @Override
    public List<Consulta> obtenerConsultasByUsuario(Usuario usuario) {
        String hql ="FROM Consulta WHERE usuario=: usuario ";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
}
