package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProyecto {
    void guardar(Proyecto proyecto);

    List<Proyecto> obtener();

    void actualizar(Proyecto proyecto);
}
