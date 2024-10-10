package com.tallerwebi.dominio.excepcion;

public class UsuarioInexistenteException extends RuntimeException {
    public UsuarioInexistenteException(String mensaje) {
        super(mensaje);
    }
}
