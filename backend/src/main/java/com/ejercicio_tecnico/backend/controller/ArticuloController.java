package com.ejercicio_tecnico.backend.controller;

import com.ejercicio_tecnico.backend.dto.ArticuloDto;
import com.ejercicio_tecnico.backend.service.ArticuloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulos")
@RequiredArgsConstructor
public class ArticuloController {

    private final ArticuloService articuloService;

    @PostMapping("/crear-articulo")
    public ResponseEntity<ArticuloDto> crearArticulo(@RequestBody ArticuloDto articuloDto) {
        ArticuloDto nuevoArticulo = articuloService.crearArticulo(articuloDto);
        return new ResponseEntity<>(nuevoArticulo, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar-articulo")
    public ResponseEntity<ArticuloDto> actualizarArticulo(@RequestBody ArticuloDto articuloDto) {
        ArticuloDto articuloActualizado = articuloService.actualizarArticulo(articuloDto);
        return new ResponseEntity<>(articuloActualizado, HttpStatus.OK);
    }

    @GetMapping("/obtener-articulo/{id}")
    public ResponseEntity<ArticuloDto> obtenerArticuloPorId(@PathVariable Long id) {
        ArticuloDto articulo = articuloService.obtenerArticuloPorId(id);
        return new ResponseEntity<>(articulo, HttpStatus.OK);
    }

    @GetMapping("/listar-articulos")
    public ResponseEntity<List<ArticuloDto>> listarArticulos() {
        List<ArticuloDto> articulos = articuloService.listarArticulos();
        return new ResponseEntity<>(articulos, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar-articulo/{id}")
    public ResponseEntity<Void> eliminarArticulo(@PathVariable Long id) {
        articuloService.eliminarArticulo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
