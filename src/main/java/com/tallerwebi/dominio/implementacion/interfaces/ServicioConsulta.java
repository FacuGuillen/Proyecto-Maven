package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Consulta;

import java.util.List;

public interface ServicioConsulta {
    void agregarConsulta(Long idUsuario, Consulta consulta) throws UsuarioNoEncontradoException;

    List<Consulta> listarConsultasByIdUsuario(Long idUsuario) throws UsuarioNoEncontradoException;

    List<Consulta> getListado();

    Consulta buscarPorId(Long consultaId) throws ConsultaNoEncontradaException;
}
