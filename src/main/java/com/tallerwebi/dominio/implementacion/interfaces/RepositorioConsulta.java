package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Usuario;

import java.util.List;

public interface RepositorioConsulta {
    void save(Consulta consulta);

    List<Consulta> obtenerConsultasByUsuario(Usuario usuario);

    Consulta findById(Long consultaId);

    List<Consulta> listarTodas();
}
