package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioComentario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioUsuario;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioComentario;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("servicioComentario")
@Transactional
public class ServicioComentarioImpl  implements ServicioComentario {
    private RepositorioComentario repositorioComentario;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioConsulta repositorioConsulta;

    @Autowired
    public ServicioComentarioImpl(RepositorioComentario repositorioComentario, RepositorioUsuario repositorioUsuario, RepositorioConsulta repositorioConsulta){
        this.repositorioComentario = repositorioComentario;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioConsulta = repositorioConsulta;
    }

    @Override
    public void agregarComentario(Long consultaId, Long idUsuario, Comentario comentario) throws UsuarioNoEncontradoException, ConsultaNoEncontradaException, UsuarioSinPermisosException {
        Usuario usuario = this.repositorioUsuario.findById(idUsuario);
        Consulta consulta = this.repositorioConsulta.findById(consultaId);
        if(usuario == null){
            throw new UsuarioNoEncontradoException("El usuario no fue encontrado");
        }
        if(consulta == null){
            throw new ConsultaNoEncontradaException("La consulta no fue encontrada");
        }
        if(usuario instanceof Profesional){
            comentario.setConsulta(consulta);
            comentario.setUsuario(usuario);
            comentario.setFechaCreacion(LocalDateTime.now());
            this.repositorioComentario.save(comentario);
        } else {
            throw new UsuarioSinPermisosException("El usuario no tiene permiso para realizar un comentario");
        }

    }

    @Override
    public List<Comentario> listarComentariosByConsulta(Consulta consulta) {
        return this.repositorioComentario.getByConsulta(consulta);
    }

    @Override
    public Comentario buscarPorId(Long comentarioId) {
        return this.repositorioComentario.findById(comentarioId);
    }

    @Override
    public void actualizarComentario(Comentario comentario) {
        this.repositorioComentario.actualizar(comentario);
    }
}
