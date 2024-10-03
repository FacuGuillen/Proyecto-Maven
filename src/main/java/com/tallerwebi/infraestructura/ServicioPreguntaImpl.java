package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.Pregunta;
import com.tallerwebi.dominio.repositorio.RepositorioPregunta;
import com.tallerwebi.dominio.ServicioPregunta;
import com.tallerwebi.dominio.modelo.enums.TipoProyecto;
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
