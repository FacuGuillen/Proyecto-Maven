package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.excepcion.UsuarioExistenteException;
import com.tallerwebi.dominio.modelo.Usuario;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistenteException;

}
