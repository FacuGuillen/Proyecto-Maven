package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Pregunta;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioPregunta;
import com.tallerwebi.dominio.modelo.enums.TipoProyecto;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("repositorioPregunta")
public class RepositorioPreguntaImpl implements RepositorioPregunta {
    private SessionFactory sessionFactory;
    public RepositorioPreguntaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Pregunta> getPreguntasPorTipoDeProyecto(TipoProyecto tipoProyecto) {
        String hql = "FROM Pregunta p WHERE p.tipoProyecto = :tipoProyecto";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("tipoProyecto", tipoProyecto);

        return query.getResultList();
    }
}
