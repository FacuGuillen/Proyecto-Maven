package com.tallerwebi.dominio.excepcion;

public class UsuarioConEmailNullException extends RuntimeException {
    public UsuarioConEmailNullException(String mensaje) {
        super(mensaje);

    }
}
