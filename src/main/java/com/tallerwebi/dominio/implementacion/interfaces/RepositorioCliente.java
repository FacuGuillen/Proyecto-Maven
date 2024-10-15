package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Cliente;

import java.util.List;

public interface RepositorioCliente {
    void guardar(Cliente cliente);

    Cliente buscarPorEmail(String email);

    List<Cliente> listar();

    Cliente buscarPorId(Long id);

    void eliminar(Cliente cliente);

    void actualizar(Cliente clienteExistente);
}

//