package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.excepcion.UsuarioConEmailNullException;
import com.tallerwebi.dominio.excepcion.UsuarioConNombreNullException;
import com.tallerwebi.dominio.excepcion.UsuarioConPasswordNullException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioProfesional;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioProfesional;
import com.tallerwebi.dominio.modelo.Profesional;

import java.util.List;

public class ServicioProfesionalImpl implements ServicioProfesional {
    private RepositorioProfesional repositorioProfesional;

    public ServicioProfesionalImpl(RepositorioProfesional repositorioProfesional) {
        this.repositorioProfesional = repositorioProfesional;
    }

    @Override
    public void guardar(Profesional profesional) {
        if(profesional.getNombre()==null){
            throw new UsuarioConNombreNullException("El nombre del profesional no puede ser nulo");
        }
        if (profesional.getEmail()==null){
            throw new UsuarioConEmailNullException("El email del profesional no puede ser nulo");
        }
        if (profesional.getPassword()==null){
            throw new UsuarioConPasswordNullException("El password del profesional no puede ser nulo");
        }
        repositorioProfesional.guardar(profesional);
    }

    @Override
    public void actualizar(Profesional profesional) {
        repositorioProfesional.actualizar(profesional);
    }

    @Override
    public void eliminar(Profesional profesional) {
        if (profesional == null) {
            throw new UsuarioInexistenteException("El profesional que intenta eliminar no existe");
        }
        repositorioProfesional.eliminar(profesional);
    }

    @Override
    public void eliminar(Long profesionalId) {
        if (buscarPorId(profesionalId) == null) {
            throw new UsuarioInexistenteException("El profesional que intenta eliminar no existe");
        }
        repositorioProfesional.eliminar(buscarPorId(profesionalId));
    }

    @Override
    public List<Profesional> listar() {
        return this.repositorioProfesional.listar();
    }

    @Override
    public Profesional buscarPorId(Long id) {
        return this.repositorioProfesional.buscarPorId(id);
    }

    @Override
    public Profesional buscarProfesionalPorEmail(String email) {
        return this.repositorioProfesional.buscarPorEmail(email);
    }
}
