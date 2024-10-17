package com.tallerwebi.dominio.modelo;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente")
    private List<Proyecto> proyectos;

    @OneToMany(mappedBy = "clienteForm")
    private List<FormSatisfaction> valoraciones;

    @OneToMany(mappedBy = "clientePublicacion")
    private List<Publicacion> publicaciones;

    public Cliente() {
        super();
    }


    // Getters y Setters
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public List<FormSatisfaction> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<FormSatisfaction> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }


    //Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getProyectos(), cliente.getProyectos()) && Objects.equals(getValoraciones(), cliente.getValoraciones()) && Objects.equals(getPublicaciones(), cliente.getPublicaciones());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProyectos(), getValoraciones(), getPublicaciones());
    }
}
