package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.Consulta;

import java.util.List;

public interface ServicioComentario {
    void agregarComentario(Long consultaId, Long idUsuario, Comentario comentario) throws UsuarioNoEncontradoException, ConsultaNoEncontradaException, UsuarioSinPermisosException;

    List<Comentario> listarComentariosByConsulta(Consulta consulta);

    Comentario buscarPorId(Long comentarioId);

    void actualizarComentario(Comentario comentario);
}
