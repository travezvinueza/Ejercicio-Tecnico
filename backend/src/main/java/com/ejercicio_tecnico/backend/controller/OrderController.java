package com.ejercicio_tecnico.backend.controller;

import com.ejercicio_tecnico.backend.dto.OrderDto;
import com.ejercicio_tecnico.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<OrderDto> crearOrden(@RequestBody OrderDto orderDto) {
        OrderDto nuevaOrden = orderService.createOrder(orderDto);
        return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
    }

    @PutMapping("/update-order")
    public ResponseEntity<OrderDto> actualizarOrden(@RequestBody OrderDto orderDto) {
        OrderDto ordenActualizada = orderService.updateOrder(orderDto);
        return new ResponseEntity<>(ordenActualizada, HttpStatus.OK);
    }

    @GetMapping("/getById-order/{id}")
    public ResponseEntity<OrderDto> obtenerOrdenPorId(@PathVariable Long id) {
        OrderDto orden = orderService.getByIdOrder(id);
        return new ResponseEntity<>(orden, HttpStatus.OK);
    }

    @GetMapping("/list-orders")
    public ResponseEntity<List<OrderDto>> listarOrdenes() {
        List<OrderDto> ordenes = orderService.listOrders();
        return new ResponseEntity<>(ordenes, HttpStatus.OK);
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
