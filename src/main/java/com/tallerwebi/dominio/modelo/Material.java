//package com.tallerwebi.dominio.modelo;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//public class Material {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String nombre;
//
//    private Double cantidad;
//
//    private String unidad;
//
//    private Double precio;
//
//    @ManyToOne
//    @JoinColumn(name = "cliente_id")
//    private Cliente clienteMaterial;
//
//
//    // Getters y Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public Double getCantidad() {
//        return cantidad;
//    }
//
//    public void setCantidad(Double cantidad) {
//        this.cantidad = cantidad;
//    }
//
//    public String getUnidad() {
//        return unidad;
//    }
//
//    public void setUnidad(String unidad) {
//        this.unidad = unidad;
//    }
//
//    public Cliente getClienteMaterial() {
//        return clienteMaterial;
//    }
//
//    public void setClienteMaterial(Cliente cliente) {
//        this.clienteMaterial = cliente;
//    }
//
//    public Double getPrecio() {
//        return precio;
//    }
//
//    public void setPrecio(Double precio) {
//        this.precio = precio;
//    }
//
//
//    // Hash and Equals
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Material)) return false;
//        Material material = (Material) o;
//        return Objects.equals(getId(), material.getId()) && Objects.equals(getNombre(), material.getNombre()) && Objects.equals(getCantidad(), material.getCantidad()) && Objects.equals(getUnidad(), material.getUnidad()) && Objects.equals(getPrecio(), material.getPrecio()) && Objects.equals(getClienteMaterial(), material.getClienteMaterial());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getNombre(), getCantidad(), getUnidad(), getPrecio(), getClienteMaterial());
//    }
//
//    @Override
//    public String toString() {
//        return "Material{" +
//                "nombre='" + nombre + '\'' +
//                ", cantidad=" + cantidad +
//                ", unidad='" + unidad + '\'' +
//                '}';
//    }
//}