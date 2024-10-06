package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.Pregunta;
import com.tallerwebi.dominio.modelo.enums.TipoProyecto;

import java.util.List;

public interface ServicioPregunta {
    List<Pregunta> obtenerPreguntasPorTipoProyecto(TipoProyecto tipoProyecto);
}
