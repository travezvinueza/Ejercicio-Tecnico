package com.ejercicio_tecnico.backend.service.impl;

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
        List<OrderArticle> orderArticles = orderDto.getArticles().stream().map(articleDto -> {
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
        Order orderExistente = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + orderDto.getId()));

        Client client = clientRepository.findById(orderDto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException("Cliente no encontrado con ID: " + orderDto.getClientId()));

        // Asignar el cliente a la orden existente
        orderExistente.setClient(client);

        // Asegúrate de que la lista sea mutable
        List<OrderArticle> orderArticlesExistente = new ArrayList<>(orderExistente.getOrderArticles());

        // Devolver al stock original los artículos antes de eliminarlos
        for (OrderArticle orderArticle : orderArticlesExistente) {
            Article article = orderArticle.getArticle();
            article.setStock(article.getStock() + orderArticle.getQuantity());
            articleRepository.save(article);
        }

        orderExistente.getOrderArticles().clear();
        orderArticleRepository.deleteAll(orderArticlesExistente);  // Eliminar los registros anteriores en la base de datos

        // Crear una lista para los nuevos artículos
        List<OrderArticle> nuevosOrderArticles = orderDto.getArticles().stream().map(articleDto -> {
            // Buscar el artículo
            Article article = articleRepository.findById(articleDto.getArticleId())
                    .orElseThrow(() -> new ArticleNotFoundException("Artículo no encontrado con ID: " + articleDto.getArticleId()));

            if (article.getStock() < articleDto.getQuantity()) {
                throw new ArticleNotFoundException("El artículo con ID: " + article.getId() +
                        " no tiene suficiente stock. Disponible: " + article.getStock() + ", Solicitado: " + articleDto.getQuantity());
            }

            // Ajustar el stock del artículo
            article.setStock(article.getStock() - articleDto.getQuantity());
            articleRepository.save(article);

            // Crear y asignar el detalle del artículo en la orden
            OrderArticle orderArticle = new OrderArticle();
            orderArticle.setOrder(orderExistente);
            orderArticle.setArticle(article);
            orderArticle.setQuantity(articleDto.getQuantity());
            orderArticle.setUnitPrice(article.getUnitPrice());
            orderArticle.setTotalPrice(article.getUnitPrice().multiply(BigDecimal.valueOf(articleDto.getQuantity())));

            return orderArticle;
        }).toList();

        // Asignar los nuevos artículos a la orden
        orderExistente.getOrderArticles().addAll(nuevosOrderArticles);
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
