package com.ejercicio_tecnico.backend.service;

import com.ejercicio_tecnico.backend.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto);
    ClientDto getByIdClient(Long id);
    List<ClientDto> listClients();
    ClientDto updateClient(Long id, ClientDto clientDto);
    void deleteClient(Long id);
}
