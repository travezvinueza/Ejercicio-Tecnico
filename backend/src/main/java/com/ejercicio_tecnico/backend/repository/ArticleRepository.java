package com.ejercicio_tecnico.backend.repository;

import com.ejercicio_tecnico.backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByName(String name);
    Article findTopByOrderByIdDesc();
    @Modifying
    @Query("UPDATE Article a SET a.order = null WHERE a.order.id = :orderId")
    void clearOrderFromArticles(@Param("orderId") Long orderId);
}
