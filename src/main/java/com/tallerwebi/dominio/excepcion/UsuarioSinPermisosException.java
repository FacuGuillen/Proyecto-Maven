package com.tallerwebi.dominio.excepcion;

public class UsuarioSinPermisosException extends Exception {
    public UsuarioSinPermisosException(String message) {
        super(message);
    }
}
