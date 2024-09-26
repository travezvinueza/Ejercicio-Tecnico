package com.ejercicio_tecnico.backend.service;

import com.ejercicio_tecnico.backend.dto.ClienteDto;

import java.util.List;

public interface ClienteService {
    ClienteDto crearCliente(ClienteDto clienteDto);
    ClienteDto obtenerClientePorId(Long id);
    List<ClienteDto> listarClientes();
    ClienteDto actualizarCliente(Long id, ClienteDto clienteDto);
    void eliminarCliente(Long id);
}
