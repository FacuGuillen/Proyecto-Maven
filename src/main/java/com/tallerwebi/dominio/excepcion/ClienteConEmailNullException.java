package com.tallerwebi.dominio.excepcion;

public class ClienteConEmailNullException extends RuntimeException {
    public ClienteConEmailNullException(String mensaje) {
        super(mensaje);

    }
}
