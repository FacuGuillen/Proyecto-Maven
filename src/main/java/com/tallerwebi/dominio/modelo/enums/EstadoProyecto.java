package com.tallerwebi.dominio.modelo.enums;

public enum EstadoProyecto {
    POR_INICIAR,
    INICIADO,
    TERMINADO,
    CANCELADO;

    public String getNombreAmigable() {
        switch (this) {
            case POR_INICIAR:
                return "Por Iniciar";
            case INICIADO:
                return "Iniciado";
            case TERMINADO:
                return "Terminado";
            case CANCELADO:
                return "Cancelado";
            default:
                return this.name(); // Retorna el nombre del enum por defecto
        }
    }
}
