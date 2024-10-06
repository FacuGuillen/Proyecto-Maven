package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Consulta;

import java.util.List;

public interface ServicioConsulta {
    void agregarConsulta(Long idUsuario, Consulta consulta) throws UsuarioNoEncontradoException;

    List<Consulta> listarConsultasByIdUsuario(Long idUsuario) throws UsuarioNoEncontradoException;
}
