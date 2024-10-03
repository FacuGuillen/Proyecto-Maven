package com.tallerwebi.dominio;

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
