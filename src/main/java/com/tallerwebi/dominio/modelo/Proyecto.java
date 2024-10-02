package com.tallerwebi.dominio.modelo;

import com.tallerwebi.dominio.modelo.enums.EstadoProyecto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreProyecto;

    private String descripcion;

    private LocalDate fechaInicioProyecto;

    private Boolean trabajoPresencial = false;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @Enumerated(EnumType.STRING) // Opcional: para almacenar el nombre del enum como string en la base de datos
    private EstadoProyecto estadoProyecto;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombre) {
        this.nombreProyecto = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Boolean getTrabajoPresencial() {
        return trabajoPresencial;
    }

    public void setTrabajoPresencial(Boolean trabajoPresencial) {
        this.trabajoPresencial = trabajoPresencial;
    }

    public LocalDate getFechaInicioProyecto() {
        return fechaInicioProyecto;
    }

    public void setFechaInicioProyecto(LocalDate fechaInicioProyecto) {
        this.fechaInicioProyecto = fechaInicioProyecto;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }


    // Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto)) return false;
        Proyecto proyecto = (Proyecto) o;
        return Objects.equals(getId(), proyecto.getId()) && Objects.equals(getNombreProyecto(), proyecto.getNombreProyecto()) && Objects.equals(getDescripcion(), proyecto.getDescripcion()) && Objects.equals(getFechaInicioProyecto(), proyecto.getFechaInicioProyecto()) && Objects.equals(getTrabajoPresencial(), proyecto.getTrabajoPresencial()) && Objects.equals(getCliente(), proyecto.getCliente()) && Objects.equals(getProfesional(), proyecto.getProfesional()) && getEstadoProyecto() == proyecto.getEstadoProyecto();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombreProyecto(), getDescripcion(), getFechaInicioProyecto(), getTrabajoPresencial(), getCliente(), getProfesional(), getEstadoProyecto());
    }
}

