package com.tallerwebi.dominio.modelo;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class FormSatisfaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer puntuacion;

    private String critica;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente clienteForm;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getCritica() {
        return critica;
    }

    public void setCritica(String critica) {
        this.critica = critica;
    }

    public Cliente getClienteForm() {
        return clienteForm;
    }

    public void setClienteForm(Cliente clienteForm) {
        this.clienteForm = clienteForm;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    //Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormSatisfaction)) return false;
        FormSatisfaction that = (FormSatisfaction) o;
        return Objects.equals(id, that.id) && Objects.equals(puntuacion, that.puntuacion) && Objects.equals(critica, that.critica) && Objects.equals(clienteForm, that.clienteForm) && Objects.equals(profesional, that.profesional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, puntuacion, critica, clienteForm, profesional);
    }
}
