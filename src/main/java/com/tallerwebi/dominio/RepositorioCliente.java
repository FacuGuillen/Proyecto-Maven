package com.tallerwebi.dominio;

public interface RepositorioCliente {
    void guardar(Cliente cliente);
    Cliente buscarPorEmail(String email);
}