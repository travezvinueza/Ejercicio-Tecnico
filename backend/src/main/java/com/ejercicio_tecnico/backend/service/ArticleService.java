package com.ejercicio_tecnico.backend.service;

import com.ejercicio_tecnico.backend.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);
    ArticleDto updateArticle(ArticleDto articleDto);
    List<ArticleDto> listArticle();
    ArticleDto getByIdArticle(Long id);
    void deleteArticle(Long id);
}
