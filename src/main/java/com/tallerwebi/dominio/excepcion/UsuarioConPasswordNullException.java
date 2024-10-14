package com.tallerwebi.dominio.excepcion;

public class UsuarioConPasswordNullException extends RuntimeException {
    public UsuarioConPasswordNullException(String mensaje) {
        super(mensaje);
    }
}

