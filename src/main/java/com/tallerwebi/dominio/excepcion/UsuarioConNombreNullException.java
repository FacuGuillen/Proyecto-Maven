package com.tallerwebi.dominio.excepcion;

public class UsuarioConNombreNullException extends RuntimeException{

    public UsuarioConNombreNullException(String mensaje){
        super(mensaje);
    }
}
