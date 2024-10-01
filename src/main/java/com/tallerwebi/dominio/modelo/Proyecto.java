package com.tallerwebi.dominio.modelo;

import com.tallerwebi.dominio.TipoProyecto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private TipoProyecto tipoProyecto;

    private String descripcion;

    private String tipoDeTrabajo;

    private String realizadoPor;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @ManyToOne
    private Estado estado;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> comentarios = new ArrayList<>();


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoProyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public String getTipoDeTrabajo() {
        return tipoDeTrabajo;
    }

    public void setTipoDeTrabajo(String tipoDeTrabajo) {
        this.tipoDeTrabajo = tipoDeTrabajo;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    // Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto)) return false;
        Proyecto proyecto = (Proyecto) o;
        return Objects.equals(getId(), proyecto.getId()) && Objects.equals(getNombre(), proyecto.getNombre()) && getTipoProyecto() == proyecto.getTipoProyecto() && Objects.equals(getDescripcion(), proyecto.getDescripcion()) && Objects.equals(getCliente(), proyecto.getCliente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getTipoProyecto(), getDescripcion(), getCliente());
    }
}

