package com.tallerwebi.dominio.implementacion.interfaces;

import com.tallerwebi.dominio.modelo.Cliente;
import java.util.List;

public interface ServicioCliente {
    Cliente buscarClientePorEmail(String email);
    List<Cliente> listarClientes();
    void eliminarCliente(Long id);
    void actualizarCliente(Cliente cliente);

    List<Cliente> listar();

    Cliente buscarPorId(Long id);

    void guardar(Cliente cliente);

    void actualizar(Cliente cliente);

    void eliminar(Cliente cliente);

    Cliente buscarClientePorId(Long id);
}