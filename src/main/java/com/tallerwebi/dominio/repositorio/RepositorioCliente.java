package com.tallerwebi.dominio.repositorio;

import com.tallerwebi.dominio.modelo.Cliente;

public interface RepositorioCliente {
    void guardar(Cliente cliente);
    Cliente buscarPorEmail(String email);
}

//