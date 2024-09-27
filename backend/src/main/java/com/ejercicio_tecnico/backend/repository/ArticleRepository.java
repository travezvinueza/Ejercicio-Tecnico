package com.ejercicio_tecnico.backend.repository;

import com.ejercicio_tecnico.backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByName(String name);
}
