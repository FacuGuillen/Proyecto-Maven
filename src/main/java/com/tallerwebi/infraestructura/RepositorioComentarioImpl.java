package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.implementacion.interfaces.RepositorioComentario;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.Consulta;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioComentarioImpl implements RepositorioComentario {
    private final SessionFactory sessionFactory;
    @Autowired
    public RepositorioComentarioImpl (SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Comentario comentario) {
        this.sessionFactory.getCurrentSession().save(comentario);
    }

    @Override
    public List<Comentario> getByConsulta(Consulta consulta) {
        String hql = "SELECT c FROM Comentario c JOIN c.usuario u JOIN Profesional p ON u.id = p.id " +
                "WHERE c.consulta = :consulta " +
                "ORDER BY p.calificacion DESC, c.fechaCreacion DESC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("consulta", consulta);
        return query.getResultList();
    }

    @Override
    public Comentario findById(Long comentarioId) {
        String hql="FROM Comentario WHERE id=: id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", comentarioId);
        return (Comentario)query.getSingleResult();
    }

    @Override
    public void actualizar(Comentario comentario) {
        this.sessionFactory.getCurrentSession().update(comentario);
    }
}
