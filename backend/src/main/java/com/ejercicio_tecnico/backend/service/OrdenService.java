package com.ejercicio_tecnico.backend.service;

import com.ejercicio_tecnico.backend.dto.OrdenDto;

import java.util.List;

public interface OrdenService {
    OrdenDto crearOrden(OrdenDto ordenDto, Long clienteId);
    OrdenDto actualizarOrden(Long id, OrdenDto ordenDto);
    List<OrdenDto> listarOrdenes();
    OrdenDto obtenerOrdenPorId(Long id);
    void eliminarOrden(Long id);
}
