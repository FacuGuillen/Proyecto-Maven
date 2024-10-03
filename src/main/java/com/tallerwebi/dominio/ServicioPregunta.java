package com.tallerwebi.dominio;

import com.tallerwebi.dominio.modelo.enums.TipoProyecto;

import java.util.List;

public interface ServicioPregunta {
    List<Pregunta> obtenerPreguntasPorTipoProyecto(TipoProyecto tipoProyecto);
}
