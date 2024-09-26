package com.ejercicio_tecnico.backend.service;

import com.ejercicio_tecnico.backend.dto.ArticuloDto;

import java.util.List;

public interface ArticuloService {
    ArticuloDto crearArticulo(ArticuloDto articuloDto);
    ArticuloDto actualizarArticulo(ArticuloDto articuloDto);
    List<ArticuloDto> listarArticulos();
    ArticuloDto obtenerArticuloPorId(Long id);
    void eliminarArticulo(Long id);
}
