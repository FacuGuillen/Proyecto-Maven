package com.tallerwebi.dominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("servicioPregunta")
@Transactional
public class ServicioPreguntaImpl implements ServicioPregunta {

    private RepositorioPregunta repositorioPregunta;

    @Autowired
    public ServicioPreguntaImpl(RepositorioPregunta repositorioPregunta){
        this.repositorioPregunta = repositorioPregunta;
    }

    @Override
    public List<Pregunta> obtenerPreguntasPorTipoProyecto(TipoProyecto tipoProyecto) {
        return repositorioPregunta.getPreguntasPorTipoDeProyecto(tipoProyecto);
    }
}
