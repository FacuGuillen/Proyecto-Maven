package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPregunta {
    List<Pregunta> obtenerPreguntasPorTipoProyecto(TipoProyecto tipoProyecto);
}
