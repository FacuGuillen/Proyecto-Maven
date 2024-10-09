package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioConsulta;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("servicioConsulta")
@Transactional
public class ServicioConsultaImpl implements ServicioConsulta {
    private RepositorioConsulta repositorioConsulta;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioConsultaImpl(RepositorioConsulta repositorioConsulta, RepositorioUsuario repositorioUsuario){
        this.repositorioConsulta = repositorioConsulta;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void agregarConsulta(Long idUsuario, Consulta consulta) throws UsuarioNoEncontradoException, UsuarioSinPermisosException {
        Usuario usuario = repositorioUsuario.findById(idUsuario);
        if(usuario == null){
            throw new UsuarioNoEncontradoException("El usuario no fue encontrado");
        }
        if(usuario instanceof Cliente){
            consulta.setUsuario(usuario);
            consulta.setFechaCreacion(LocalDateTime.now());
            this.repositorioConsulta.save(consulta);
        } else {
            throw new UsuarioSinPermisosException("El usuario no tiene permiso para realizar una consulta");
        }

    }

    @Override
    public List<Consulta> listarConsultasByIdUsuario(Long idUsuario) throws UsuarioNoEncontradoException {
        Usuario usuario = repositorioUsuario.findById(idUsuario);
        if(usuario == null){
            throw new UsuarioNoEncontradoException("El usuario no fue encontrado");
        }
        return repositorioConsulta.obtenerConsultasByUsuario(usuario);
    }

    @Override
    public List<Consulta> getListado() {
    return this.repositorioConsulta.listarTodas();
    }

    @Override
    public Consulta buscarPorId(Long consultaId) throws ConsultaNoEncontradaException {
        Consulta consulta = this.repositorioConsulta.findById(consultaId);
        if(consulta == null){
            throw new ConsultaNoEncontradaException("La consulta no fue encontrada");
        }
        return consulta;
    }
}
