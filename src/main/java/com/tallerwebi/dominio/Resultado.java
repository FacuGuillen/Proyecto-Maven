package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "resultado_id")
    private List<Material> materiales;

    private String pasos;

    // Constructor
    public Resultado() {}
}
