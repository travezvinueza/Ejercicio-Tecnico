package com.ejercicio_tecnico.backend.service;

import com.ejercicio_tecnico.backend.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto updateOrder(OrderDto orderDto);
    List<OrderDto> listOrders();
    OrderDto getByIdOrder(Long id);
    void deleteOrder(Long id);
}
