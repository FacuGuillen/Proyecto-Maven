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

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente clientePublicacion;

    // Constructor por defecto
    public Publicacion() {
        this.fechaInicioPublicacion = LocalDate.now();
        this.publicacionPausada = Boolean.FALSE;
    }

    // Getters y Setters
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

    public Boolean getPublicacionPausada() {
        return publicacionPausada;
    }

    public void setPublicacionPausada(Boolean publicacionPausada) {
        this.publicacionPausada = publicacionPausada;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Cliente getClientePublicacion() {
        return clientePublicacion;
    }

    public void setClientePublicacion(Cliente clientePublicacion) {
        this.clientePublicacion = clientePublicacion;
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publicacion that = (Publicacion) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(precio, that.precio) &&
                Objects.equals(fechaInicioPublicacion, that.fechaInicioPublicacion) &&
                Objects.equals(fechaFinPublicacion, that.fechaFinPublicacion) &&
                Objects.equals(publicacionPausada, that.publicacionPausada) &&
                Objects.equals(clientePublicacion, that.clientePublicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, stock, precio, fechaInicioPublicacion, fechaFinPublicacion, publicacionPausada, clientePublicacion);
    }
}