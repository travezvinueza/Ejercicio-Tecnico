package com.ejercicio_tecnico.backend.controller;

import com.ejercicio_tecnico.backend.dto.ClientDto;
import com.ejercicio_tecnico.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create-client")
    public ResponseEntity<ClientDto> crearCliente(@RequestBody ClientDto clientDto) {
        ClientDto nuevoCliente = clientService.createClient(clientDto);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @GetMapping("/getById-client/{id}")
    public ResponseEntity<ClientDto> obtenerClientePorId(@PathVariable Long id) {
        ClientDto cliente = clientService.getByIdClient(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/list-clients")
    public ResponseEntity<List<ClientDto>> listarClientes() {
        List<ClientDto> clientes = clientService.listClients();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PutMapping("/update-client/{id}")
    public ResponseEntity<ClientDto> actualizarCliente(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        ClientDto clienteActualizado = clientService.updateClient(id, clientDto);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/delete-client/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
