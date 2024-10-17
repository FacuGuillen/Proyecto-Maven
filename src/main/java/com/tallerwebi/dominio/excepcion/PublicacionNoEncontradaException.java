package com.tallerwebi.dominio.excepcion;

public class PublicacionNoEncontradaException extends RuntimeException {
    public PublicacionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
