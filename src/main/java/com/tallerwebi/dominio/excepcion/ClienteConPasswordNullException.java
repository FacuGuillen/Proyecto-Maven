package com.tallerwebi.dominio.excepcion;

public class ClienteConPasswordNullException extends RuntimeException {
    public ClienteConPasswordNullException(String mensaje) {
        super(mensaje);
    }
}

