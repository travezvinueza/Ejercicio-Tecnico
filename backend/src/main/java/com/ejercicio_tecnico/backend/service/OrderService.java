package com.ejercicio_tecnico.backend.service;

import com.ejercicio_tecnico.backend.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto crearOrden(OrderDto orderDto);
    OrderDto actualizarOrden(OrderDto orderDto);
    List<OrderDto> listarOrdenes();
    OrderDto obtenerOrdenPorId(Long id);
    void eliminarOrden(Long id);
}
