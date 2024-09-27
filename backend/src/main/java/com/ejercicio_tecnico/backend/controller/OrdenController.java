package com.ejercicio_tecnico.backend.controller;

import com.ejercicio_tecnico.backend.dto.OrdenDto;
import com.ejercicio_tecnico.backend.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    @PostMapping("/crear/orden")
    public ResponseEntity<OrdenDto> crearOrden(@RequestBody OrdenDto ordenDto) {
        OrdenDto nuevaOrden = ordenService.crearOrden(ordenDto);
        return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar-orden")
    public ResponseEntity<OrdenDto> actualizarOrden(@RequestBody OrdenDto ordenDto) {
        OrdenDto ordenActualizada = ordenService.actualizarOrden(ordenDto);
        return new ResponseEntity<>(ordenActualizada, HttpStatus.OK);
    }

    @GetMapping("/obtener-orden/{id}")
    public ResponseEntity<OrdenDto> obtenerOrdenPorId(@PathVariable Long id) {
        OrdenDto orden = ordenService.obtenerOrdenPorId(id);
        return new ResponseEntity<>(orden, HttpStatus.OK);
    }

    @GetMapping("/listar-ordenes")
    public ResponseEntity<List<OrdenDto>> listarOrdenes() {
        List<OrdenDto> ordenes = ordenService.listarOrdenes();
        return new ResponseEntity<>(ordenes, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar-orden/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        ordenService.eliminarOrden(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
