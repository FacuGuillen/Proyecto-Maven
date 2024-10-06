package com.tallerwebi.dominio.modelo.enums;

public enum TipoConsulta {
    PISO("Piso"),
    PARED("Pared"),
    TECHO("Techo");

    private final String nombre;

    TipoConsulta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
