package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.ArticleDto;
import com.ejercicio_tecnico.backend.dto.OrderDto;
import com.ejercicio_tecnico.backend.entity.Article;
import com.ejercicio_tecnico.backend.entity.Client;
import com.ejercicio_tecnico.backend.entity.Order;
import com.ejercicio_tecnico.backend.exception.ArticleNotFoundException;
import com.ejercicio_tecnico.backend.exception.ClientNotFoundException;
import com.ejercicio_tecnico.backend.exception.OrderNotFoundException;
import com.ejercicio_tecnico.backend.repository.ArticleRepository;
import com.ejercicio_tecnico.backend.repository.ClientRepository;
import com.ejercicio_tecnico.backend.repository.OrderRepository;
import com.ejercicio_tecnico.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Client client = clientRepository.findById(orderDto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + orderDto.getClientId()));

        Order order = modelMapper.map(orderDto, Order.class);
        order.setCode(generateCodigoUnico());
        order.setDate(LocalDate.now());
        order.setClient(client);

        List<Article> articles = orderDto.getArticles().stream().map(articuloDto -> {
            Article article = modelMapper.map(articuloDto, Article.class);
            article.setOrder(order);
            return article;
        }).toList();

        order.setArticles(articles);
        Order nuevaOrder = orderRepository.save(order);
        return modelMapper.map(nuevaOrder, OrderDto.class);
    }

    private String generateCodigoUnico() {
        return "ORDEN-" + UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        Order orderExistente = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + orderDto.getId()));

        Client client = clientRepository.findById(orderDto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + orderDto.getClientId()));
        orderExistente.setClient(client);

        // Crear una lista para los nuevos artículos
        List<Article> articulosActualizados = new ArrayList<>();
        List<Article> articulosActuales = orderExistente.getArticles();

        // Agregar los artículos nuevos y actualizar su referencia
        for (ArticleDto articuloDto : orderDto.getArticles()) {
            Article article = articleRepository.findById(articuloDto.getId())
                    .orElseThrow(() -> new ArticleNotFoundException("Artículo no encontrado con ID: " + articuloDto.getId()));

            article.setOrder(orderExistente);
            articulosActualizados.add(article);
        }

        for (Article articuloActual : articulosActuales) {
            if (!articulosActualizados.contains(articuloActual)) {
                articuloActual.setOrder(null);
            }
        }

        orderExistente.setArticles(articulosActualizados);
        Order orderActualizada = orderRepository.save(orderExistente);
        return modelMapper.map(orderActualizada, OrderDto.class);
    }

    @Override
    public List<OrderDto> listOrders() {
        List<Order> ordenes = orderRepository.findAll();
        return ordenes.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
    }

    @Override
    public OrderDto getByIdOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + id));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + id));
        orderRepository.delete(order);
    }
}
