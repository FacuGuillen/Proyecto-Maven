package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.implementacion.interfaces.ServicioConsulta;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioConsulta;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void agregarConsulta(Long idUsuario, Consulta consulta) throws UsuarioNoEncontradoException {
        Usuario usuario = repositorioUsuario.findById(idUsuario);
        if(usuario == null){
            throw new UsuarioNoEncontradoException("El usuario no fue encontrado");
        }
        consulta.setUsuario(usuario);
        this.repositorioConsulta.save(consulta);
    }

    @Override
    public List<Consulta> listarConsultasByIdUsuario(Long idUsuario) throws UsuarioNoEncontradoException {
        Usuario usuario = repositorioUsuario.findById(idUsuario);
        if(usuario == null){
            throw new UsuarioNoEncontradoException("El usuario no fue encontrado");
        }
        return repositorioConsulta.obtenerConsultasByUsuario(usuario);
    }
}
