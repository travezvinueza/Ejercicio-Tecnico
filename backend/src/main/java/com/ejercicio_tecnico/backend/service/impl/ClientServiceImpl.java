package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.ClientDto;
import com.ejercicio_tecnico.backend.entity.Client;
import com.ejercicio_tecnico.backend.exception.ClientNotFoundException;
import com.ejercicio_tecnico.backend.repository.ClientRepository;
import com.ejercicio_tecnico.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        if (clientRepository.existsByName(clientDto.getName())) {
            throw new IllegalArgumentException("El cliente con el nombre " + clientDto.getName() + " ya existe.");
        }
        Client client = modelMapper.map(clientDto, Client.class);
        Client nuevoClient = clientRepository.save(client);
        return modelMapper.map(nuevoClient, ClientDto.class);
    }

    @Override
    public ClientDto getByIdClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + id));
        return modelMapper.map(client, ClientDto.class);
    }

    @Override
    public List<ClientDto> listClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .toList();
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client clientExistente = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + id));

        clientExistente.setName(clientDto.getName());
        clientExistente.setLastname(clientDto.getLastname());

        Client clientActualizado = clientRepository.save(clientExistente);
        return modelMapper.map(clientActualizado, ClientDto.class);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + id));
        clientRepository.delete(client);
    }
}
