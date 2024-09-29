package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.ArticleDto;
import com.ejercicio_tecnico.backend.entity.Article;
import com.ejercicio_tecnico.backend.exception.ArticleNotFoundException;
import com.ejercicio_tecnico.backend.repository.ArticleRepository;
import com.ejercicio_tecnico.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        if (articleRepository.existsByName(articleDto.getName())) {
            throw new IllegalArgumentException("El articulo con el nombre " + articleDto.getName() + " ya existe.");
        }

        Article article = modelMapper.map(articleDto, Article.class);
        article.setCode(generateCodigoUnico());

        Article nuevoArticle = articleRepository.save(article);
        return modelMapper.map(nuevoArticle, ArticleDto.class);
    }

    private String generateCodigoUnico() {
        Article lastArticle = articleRepository.findTopByOrderByIdDesc();
        long articuloCount = (lastArticle != null) ? lastArticle.getId() + 1 : 1;
        return String.format("ARTICULO-%06d", articuloCount);
    }

    @Override
    public ArticleDto updateArticle(ArticleDto articleDto) {
        Article article = articleRepository.findById(articleDto.getId())
                .orElseThrow(() -> new ArticleNotFoundException("Artículo no encontrado con ID: " + articleDto.getId()));

        article.setName(articleDto.getName());
        article.setUnitPrice(articleDto.getUnitPrice());

        Article articleActualizado = articleRepository.save(article);
        return modelMapper.map(articleActualizado, ArticleDto.class);
    }


    @Override
    public List<ArticleDto> listArticle() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(article -> modelMapper.map(article, ArticleDto.class))
                .toList();
    }

    @Override
    public ArticleDto getByIdArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Artículo no encontrado con ID: " + id));
        return modelMapper.map(article, ArticleDto.class);
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Artículo no encontrado con ID: " + id));
        articleRepository.delete(article);
    }
}
