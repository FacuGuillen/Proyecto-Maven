package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.excepcion.UsuarioConEmailNullException;
import com.tallerwebi.dominio.excepcion.UsuarioConNombreNullException;
import com.tallerwebi.dominio.excepcion.UsuarioConPasswordNullException;
import com.tallerwebi.dominio.excepcion.UsuarioInexistenteException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioCliente;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioClienteImpl implements ServicioCliente {

    private RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioClienteImpl(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
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
        } else {
                throw new UsuarioInexistenteException("El cliente que intenta eliminar no existe");

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
        if(cliente.getNombre()==null){
            throw new UsuarioConNombreNullException("El nombre del cliente no puede ser nulo");
        }
        if (cliente.getEmail()==null){
            throw new UsuarioConEmailNullException("El email del cliente no puede ser nulo");
        }
        if (cliente.getPassword()==null){
            throw new UsuarioConPasswordNullException("El password del cliente no puede ser nulo");
        }
            repositorioCliente.guardar(cliente);
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

        if (cliente == null) {
            throw new UsuarioInexistenteException("El cliente que intenta eliminar no existe");
        }
        repositorioCliente.eliminar(cliente);
    }

    @Transactional
    @Override
    public Cliente buscarClientePorId(Long id) {
        return repositorioCliente.buscarPorId(id);
    }
}