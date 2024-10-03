package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.modelo.Consulta;

import java.util.List;

public interface ServicioConsulta {
    void agregarConsulta(Long idUsuario, Consulta consulta) throws UsuarioNoEncontrado;

    List<Consulta> listarConsultasByIdUsuario(Long idUsuario) throws UsuarioNoEncontrado;
}
