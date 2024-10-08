package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.Consulta;

import java.util.List;

public interface RepositorioComentario {
    void save(Comentario comentario);

    List<Comentario> getByConsulta(Consulta consulta);
}
