package com.tallerwebi.dominio.excepcion;

public class ComentarioInexistenteEnBaseDeDatos extends RuntimeException {
    public ComentarioInexistenteEnBaseDeDatos(String mensaje) {
        super(mensaje);
    }
}
