package com.ejercicio_tecnico.backend.repository;

import com.ejercicio_tecnico.backend.entity.OrderArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderArticleRepository extends JpaRepository<OrderArticle, Long> {
}
