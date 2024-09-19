package com.tallerwebi.dominio;
import java.util.List;

public class Proyecto {
private Long id;
private String nombre;
private Long usuario_id;
private enum tipoProyecto{
    TECHO, PARED, PISO
};

private Boolean estado_id;
//private List<Profesional> profesionales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }



    public Boolean getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(Boolean estado_id) {
        this.estado_id = estado_id;
    }

  /*  public List<Profesional> getProfesionales() {
        return profesionales;
    }

    public void setProfesionales(List<Profesional> profesionales) {
        this.profesionales = profesionales;
    }*/
}
