package com.ejercicio_tecnico.backend.controller;

import com.ejercicio_tecnico.backend.dto.ClienteDto;
import com.ejercicio_tecnico.backend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/crear-cliente")
    public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto) {
        ClienteDto nuevoCliente = clienteService.crearCliente(clienteDto);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @GetMapping("/obtener-cliente/{id}")
    public ResponseEntity<ClienteDto> obtenerClientePorId(@PathVariable Long id) {
        ClienteDto cliente = clienteService.obtenerClientePorId(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/listar-clientes")
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        List<ClienteDto> clientes = clienteService.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PutMapping("/actualizar-cliente/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDto clienteDto) {
        ClienteDto clienteActualizado = clienteService.actualizarCliente(id, clienteDto);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar-cliente/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
