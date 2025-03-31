package com.bpichincha.microservicios.service;

import com.bpichincha.microservicios.entity.Cliente;
import com.bpichincha.microservicios.exception.RecursoNoEncontradoException;
import com.bpichincha.microservicios.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Crear un nuevo cliente
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Obtener todos los clientes
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    // Obtener un cliente por ID
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente con ID" + id + " no encontrado"));
    }

    // Actualizar un cliente
    public Cliente actualizarCliente(Long id, Cliente nuevoCliente) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(nuevoCliente.getNombre());
                    cliente.setGenero(nuevoCliente.getGenero());
                    cliente.setEdad(nuevoCliente.getEdad());
                    cliente.setDireccion(nuevoCliente.getDireccion());
                    cliente.setTelefono(nuevoCliente.getTelefono());
                    cliente.setEstado(nuevoCliente.getEstado());
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    // Eliminar un cliente
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
