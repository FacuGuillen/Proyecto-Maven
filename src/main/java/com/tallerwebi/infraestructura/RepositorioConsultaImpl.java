package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
import org.hibernate.Hibernate;
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
        List<Consulta> consultas = query.getResultList();

        for (Consulta consulta : consultas) {
            // Esto provocará que se carguen los comentarios de forma lazy
            Hibernate.initialize(consulta.getComentarios());
        }

        return consultas;
    }

    @Override
    public Consulta findById(Long consultaId) {
        String hql= "FROM Consulta WHERE id=: id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", consultaId);
        return (Consulta)query.getSingleResult();
    }

    @Override
    public List<Consulta> listarTodas() {
        String hql = "FROM Consulta ORDER BY fechaCreacion DESC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        List<Consulta> consultas = query.getResultList();

        for (Consulta consulta : consultas) {
            // Esto provocará que se carguen los comentarios de forma lazy
            Hibernate.initialize(consulta.getComentarios());
        }
        return consultas;
    }

}
