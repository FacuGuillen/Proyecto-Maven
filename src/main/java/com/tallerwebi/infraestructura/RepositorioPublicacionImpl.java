package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.implementacion.interfaces.RepositorioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Publicacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RepositorioPublicacionImpl implements RepositorioPublicacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPublicacionImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public void guardar(Publicacion publicacion) {
//        if (publicacion.getNombre() == null || publicacion.getStock() == null || publicacion.getStock() == 0 || publicacion.getPrecio() == null || publicacion.getPublicacionPausada() == null){
//            return;
//        }
        this.sessionFactory.getCurrentSession().save(publicacion);
    }


    @Override
    public void eliminar(Publicacion publicacion) {
        this.sessionFactory.getCurrentSession().delete(publicacion);
    }



/*----------------------------- LISTAS -----------------------------*/

    @Override
    public List<Publicacion> listadoPublicacion(){
        String hql = "FROM Consulta ORDER BY fechaCreacion DESC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        List<Publicacion> publicaciones = query.getResultList();

        return publicaciones;
    }

    @Override
    public List<Publicacion> listadoPublicacionPorCliente(Cliente cliente) {
        // Usamos HQL para obtener las publicaciones asociadas a un cliente específico
        String hql = "FROM Publicacion WHERE clientePublicacion = :cliente ORDER BY fechaInicioPublicacion ASC";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("cliente", cliente);

        return query.getResultList();
    }

}
