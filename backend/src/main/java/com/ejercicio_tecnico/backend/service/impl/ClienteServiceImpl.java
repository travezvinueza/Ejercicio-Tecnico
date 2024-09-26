package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.ClienteDto;
import com.ejercicio_tecnico.backend.entity.Cliente;
import com.ejercicio_tecnico.backend.exception.ClienteNotFoundException;
import com.ejercicio_tecnico.backend.repository.ClienteRepository;
import com.ejercicio_tecnico.backend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClienteDto crearCliente(ClienteDto clienteDto) {
        if (clienteRepository.existsByNombre(clienteDto.getNombre())) {
            throw new IllegalArgumentException("El cliente con el nombre " + clienteDto.getNombre() + " ya existe.");
        }
        Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
        Cliente nuevoCliente = clienteRepository.save(cliente);
        return modelMapper.map(nuevoCliente, ClienteDto.class);
    }

    @Override
    public ClienteDto obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        return modelMapper.map(cliente, ClienteDto.class);
    }

    @Override
    public List<ClienteDto> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDto.class))
                .toList();
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteDto clienteDto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));

        clienteExistente.setNombre(clienteDto.getNombre());
        clienteExistente.setApellido(clienteDto.getApellido());

        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        return modelMapper.map(clienteActualizado, ClienteDto.class);
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(cliente);
    }
}
