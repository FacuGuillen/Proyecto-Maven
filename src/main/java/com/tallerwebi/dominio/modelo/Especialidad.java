package com.tallerwebi.dominio.modelo;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "especialidades")
    private List<Profesional> profesionales;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Profesional> getProfesionales() {
        return profesionales;
    }

    public void setProfesionales(List<Profesional> profesionales) {
        this.profesionales = profesionales;
    }


    //Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Especialidad)) return false;
        Especialidad that = (Especialidad) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getProfesionales(), that.getProfesionales());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProfesionales());
    }
}
