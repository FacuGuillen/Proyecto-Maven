package com.tallerwebi.dominio.excepcion;

public class UsuarioSinPermisosException extends RuntimeException {
    public UsuarioSinPermisosException(String message) {
        super(message);
    }
}
