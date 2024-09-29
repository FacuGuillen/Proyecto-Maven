package com.tallerwebi.dominio.modelo;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Cliente extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cliente")
    private List<Proyecto> proyectos;

    @OneToMany(mappedBy = "clienteForm")
    private List<FormSatisfaction> valoraciones;

    @OneToMany(mappedBy = "clienteMaterial")
    private List<Material> materiales;


    // Getters y Setters
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<FormSatisfaction> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<FormSatisfaction> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }


    //Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId()) && Objects.equals(getProyectos(), cliente.getProyectos()) && Objects.equals(getValoraciones(), cliente.getValoraciones()) && Objects.equals(materiales, cliente.materiales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getProyectos(), getValoraciones(), materiales);
    }
}
