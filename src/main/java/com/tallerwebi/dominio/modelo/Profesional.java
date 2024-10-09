package com.tallerwebi.dominio.modelo;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Profesional extends Usuario {
    private Integer calificacion;

    @ManyToMany
    @JoinTable(
            name = "Profesional_Especialidad",
            joinColumns = @JoinColumn(name = "profesional_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private List<Especialidad> especialidades;

    @OneToMany(mappedBy = "profesional")
    private List<FormSatisfaction> valoraciones;

    public Profesional() {
        this.calificacion = 0;
    }
    // Getters and Setters
    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public List<FormSatisfaction> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<FormSatisfaction> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public String getEstrellas() {
        StringBuilder estrellas = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            if (i <= calificacion) {
                estrellas.append("<i class='fas fa-star star'></i> "); // Estrella llena
            } else {
                estrellas.append("<i class='far fa-star star'></i> "); // Estrella vacía
            }
        }
        return estrellas.toString();
    }


    //Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profesional)) return false;
        if (!super.equals(o)) return false;
        Profesional that = (Profesional) o;
        return Objects.equals(getCalificacion(), that.getCalificacion()) && Objects.equals(getEspecialidades(), that.getEspecialidades()) && Objects.equals(valoraciones, that.valoraciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCalificacion(), getEspecialidades(), valoraciones);
    }
}
