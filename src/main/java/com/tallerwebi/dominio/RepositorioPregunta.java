package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPregunta {
    List<Pregunta> getPreguntasPorTipoDeProyecto(TipoProyecto tipoProyecto);
}
