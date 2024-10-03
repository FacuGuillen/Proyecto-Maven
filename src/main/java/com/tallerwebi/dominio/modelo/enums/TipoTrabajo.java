package com.tallerwebi.dominio.modelo.enums;

public enum TipoTrabajo {
    MANTENIMIENTO("Mantenimiento"),
    CONSTRUCCION("Construccion"),
    ROTURA("Rotura");

    private final String nombre;

    TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
