package com.tallerwebi.dominio.excepcion;

public class ClienteConNombreNullException  extends RuntimeException{

    public ClienteConNombreNullException(String mensaje){
        super(mensaje);
    }
}
