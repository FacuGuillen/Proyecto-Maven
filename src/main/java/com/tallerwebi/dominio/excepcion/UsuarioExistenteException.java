package com.tallerwebi.dominio.excepcion;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }

}

