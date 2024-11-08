package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.OrderArticleDto;
import com.ejercicio_tecnico.backend.dto.OrderDto;
import com.ejercicio_tecnico.backend.entity.Article;
import com.ejercicio_tecnico.backend.entity.Client;
import com.ejercicio_tecnico.backend.entity.Order;
import com.ejercicio_tecnico.backend.entity.OrderArticle;
import com.ejercicio_tecnico.backend.exception.ArticleNotFoundException;
import com.ejercicio_tecnico.backend.exception.ClientNotFoundException;
import com.ejercicio_tecnico.backend.exception.OrderNotFoundException;
import com.ejercicio_tecnico.backend.repository.ArticleRepository;
import com.ejercicio_tecnico.backend.repository.ClientRepository;
import com.ejercicio_tecnico.backend.repository.OrderArticleRepository;
import com.ejercicio_tecnico.backend.repository.OrderRepository;
import com.ejercicio_tecnico.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final OrderArticleRepository orderArticleRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Client client = clientRepository.findById(orderDto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + orderDto.getClientId()));

        Order order = modelMapper.map(orderDto, Order.class);
        order.setCode(generateCodigoUnico());
        order.setDate(LocalDate.now());
        order.setClient(client);

        // Crear la lista para almacenar los detalles de los artículos de la orden
        List<OrderArticle> orderArticles = orderDto.getOrderArticles().stream().map(articleDto -> {
            Article article = articleRepository.findById(articleDto.getArticleId())
                    .orElseThrow(() -> new ArticleNotFoundException("Artículo no encontrado con ID: " + articleDto.getArticleId()));

            if (article.getStock() < articleDto.getQuantity()) {
                throw new ArticleNotFoundException("El artículo con ID: " + article.getId() +
                        " no tiene suficiente stock. Disponible: " + article.getStock() + ", Solicitado: " + articleDto.getQuantity());
            }

            article.setStock(article.getStock() - articleDto.getQuantity());
            articleRepository.save(article);

            // Crear el detalle del artículo de la orden
            OrderArticle orderArticle = new OrderArticle();
            orderArticle.setOrder(order);
            orderArticle.setArticle(article);
            orderArticle.setQuantity(articleDto.getQuantity());
            orderArticle.setUnitPrice(article.getUnitPrice());
            orderArticle.setTotalPrice(article.getUnitPrice().multiply(BigDecimal.valueOf(articleDto.getQuantity())));

            return orderArticle;
        }).toList();

        order.setOrderArticles(orderArticles);
        Order nuevaOrder = orderRepository.save(order);
        return modelMapper.map(nuevaOrder, OrderDto.class);
    }

    private String generateCodigoUnico() {
        return "ORDEN-" + UUID.randomUUID().toString().substring(0, 6);
    }
    

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        // Buscar la orden existente
        Order orderExistente = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + orderDto.getId()));

        Client client = clientRepository.findById(orderDto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + orderDto.getClientId()));

        // Asignar el cliente a la orden existente
        orderExistente.setClient(client);

        // Devolver al stock original los artículos antes de cualquier modificación
        for (OrderArticle orderArticle : orderExistente.getOrderArticles()) {
            Article article = orderArticle.getArticle();
            article.setStock(article.getStock() + orderArticle.getQuantity());
            articleRepository.save(article); // Ajustar el stock en base a los artículos existentes
        }

        // Limpiar la lista de artículos de la orden y persistir la eliminación
        orderExistente.getOrderArticles().clear();
        orderRepository.save(orderExistente); // Guardar para eliminar los artículos anteriores

        // Crear y agregar nuevos artículos a la orden
        List<OrderArticle> nuevosOrderArticles = new ArrayList<>();
        for (OrderArticleDto articleDto : orderDto.getOrderArticles()) {
            Article article = articleRepository.findById(articleDto.getArticleId())
                    .orElseThrow(() -> new ArticleNotFoundException("Artículo no encontrado con ID: " + articleDto.getArticleId()));

            if (article.getStock() < articleDto.getQuantity()) {
                throw new ArticleNotFoundException("El artículo con ID: " + article.getId() +
                        " no tiene suficiente stock. Disponible: " + article.getStock() + ", Solicitado: " + articleDto.getQuantity());
            }

            // Crear un nuevo detalle de artículo para la orden
            OrderArticle orderArticle = new OrderArticle();
            orderArticle.setOrder(orderExistente);
            orderArticle.setArticle(article);
            orderArticle.setQuantity(articleDto.getQuantity());
            orderArticle.setUnitPrice(article.getUnitPrice());
            orderArticle.setTotalPrice(article.getUnitPrice().multiply(BigDecimal.valueOf(articleDto.getQuantity())));

            // Ajustar el stock del artículo
            article.setStock(article.getStock() - articleDto.getQuantity());
            articleRepository.save(article); // Guardar el ajuste del stock

            // Agregar el nuevo artículo a la lista
            nuevosOrderArticles.add(orderArticle);
        }

        // Asignar los nuevos artículos a la orden
        orderExistente.getOrderArticles().addAll(nuevosOrderArticles);

        // Guardar la orden actualizada
        Order ordenActualizada = orderRepository.save(orderExistente);
        return modelMapper.map(ordenActualizada, OrderDto.class);
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
        List<OrderArticle> orderArticles = order.getOrderArticles();

        orderArticles.forEach(orderArticle -> {
            Article article = orderArticle.getArticle();
            article.setStock(article.getStock() + orderArticle.getQuantity());
            articleRepository.save(article);
        });

        orderArticleRepository.deleteAll(orderArticles);
        orderRepository.delete(order);
    }

}
