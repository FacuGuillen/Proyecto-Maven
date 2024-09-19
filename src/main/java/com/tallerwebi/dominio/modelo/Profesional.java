package com.tallerwebi.dominio.modelo;

import com.tallerwebi.dominio.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
public class Profesional extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer calificacion;
    @ManyToOne
    private Especialidad especialidad;


}
