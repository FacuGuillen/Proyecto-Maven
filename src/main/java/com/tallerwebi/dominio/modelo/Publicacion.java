package com.tallerwebi.dominio.modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer stock;

    private Double precio;

    private LocalDate fechaInicioPublicacion;

    private LocalDate fechaFinPublicacion;

    private Boolean publicacionPausada;

    private String urlImagen;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente clientePublicacion;


    // Constructor
    public Publicacion() {
        this.fechaInicioPublicacion = LocalDate.now();
        this.publicacionPausada = Boolean.FALSE;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaInicioPublicacion() {
        return fechaInicioPublicacion;
    }

    public void setFechaInicioPublicacion(LocalDate fechaInicioPublicacion) {
        this.fechaInicioPublicacion = fechaInicioPublicacion;
    }

    public LocalDate getFechaFinPublicacion() {
        return fechaFinPublicacion;
    }

    public void setFechaFinPublicacion(LocalDate fechaFinPublicacion) {
        this.fechaFinPublicacion = fechaFinPublicacion;
    }

    public Cliente getClientePublicacion() {
        return clientePublicacion;
    }

    public void setClientePublicacion(Cliente clientePublicacion) {
        this.clientePublicacion = clientePublicacion;
    }

    public Boolean getPublicacionPausada() {
        return publicacionPausada;
    }

    public void setPublicacionPausada(Boolean publicacionPausada) {
        this.publicacionPausada = publicacionPausada;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    // Hash and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publicacion)) return false;
        Publicacion that = (Publicacion) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNombre(), that.getNombre()) && Objects.equals(getStock(), that.getStock()) && Objects.equals(getPrecio(), that.getPrecio()) && Objects.equals(getFechaInicioPublicacion(), that.getFechaInicioPublicacion()) && Objects.equals(getFechaFinPublicacion(), that.getFechaFinPublicacion()) && Objects.equals(publicacionPausada, that.publicacionPausada) && Objects.equals(getClientePublicacion(), that.getClientePublicacion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getStock(), getPrecio(), getFechaInicioPublicacion(), getFechaFinPublicacion(), publicacionPausada, getClientePublicacion());
    }
}
