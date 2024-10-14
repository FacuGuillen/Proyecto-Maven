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
import javax.persistence.TypedQuery;
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
    public Publicacion obtenerPublicacionPorId(Long idPublicacion) {
        // Obtén la sesión actual de Hibernate
        Session session = this.sessionFactory.getCurrentSession();

        // Busca la publicación por su ID
        return session.get(Publicacion.class, idPublicacion);
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


    /*----------------------------- ELIMINAR -----------------------------*/
    @Override
    public void eliminarPublicacion(Publicacion publicacion) {
        this.sessionFactory.getCurrentSession().delete(publicacion);
    }

    @Override
    public List<Publicacion> buscarPublicacionPorNombre(String nombre) {
        String hql = "FROM Publicacion WHERE lower(nombre) LIKE :nombre";
        TypedQuery<Publicacion> query = sessionFactory.getCurrentSession().createQuery(hql, Publicacion.class);
        query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
        return query.getResultList();
    }


}
