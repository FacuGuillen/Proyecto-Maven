package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.implementacion.interfaces.ServicioCliente;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioClienteImpl implements ServicioCliente {

    private final RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioClienteImpl(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    @Override
    @Transactional
    public void guardarCliente(Cliente cliente) {
        if (cliente.getId() == null) {
            repositorioCliente.guardar(cliente);
        } else {
            actualizarCliente(cliente);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarClientePorEmail(String email) {
        return repositorioCliente.buscarPorEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return repositorioCliente.listar();
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = repositorioCliente.buscarPorId(id);
        if (cliente != null) {
            repositorioCliente.eliminar(cliente);
        }
    }

    @Override
    @Transactional
    public void actualizarCliente(Cliente cliente) {
        Cliente clienteExistente = repositorioCliente.buscarPorId(cliente.getId());
        if (clienteExistente != null) {
            clienteExistente.setNombre(cliente.getNombre());
            clienteExistente.setEmail(cliente.getEmail());
            clienteExistente.setTelefono(cliente.getTelefono());
            repositorioCliente.actualizar(clienteExistente);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return repositorioCliente.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return repositorioCliente.buscarPorId(id);

        // VALIDAR CLIENTE ENCONTRADO POR ID

    }

    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        if (cliente.getId() == null) {
            repositorioCliente.guardar(cliente);
        } else {
            actualizar(cliente);
        }
    }

    @Override
    @Transactional
    public void actualizar(Cliente cliente) {
        Cliente clienteExistente = repositorioCliente.buscarPorId(cliente.getId());
        if (clienteExistente != null) {
            clienteExistente.setNombre(cliente.getNombre());
            clienteExistente.setEmail(cliente.getEmail());
            clienteExistente.setTelefono(cliente.getTelefono());
            repositorioCliente.actualizar(clienteExistente);
        }
    }

    @Override
    @Transactional
    public void eliminar(Cliente cliente) {
        Cliente clienteExistente = repositorioCliente.buscarPorId(cliente.getId());
        if (clienteExistente != null) {
            repositorioCliente.eliminar(clienteExistente);
        }
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        return repositorioCliente.buscarPorId(id);
    }
}