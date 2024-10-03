package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.Pregunta;
import com.tallerwebi.dominio.modelo.enums.TipoProyecto;

import java.util.List;

public interface RepositorioPregunta {
    List<Pregunta> getPreguntasPorTipoDeProyecto(TipoProyecto tipoProyecto);
}
