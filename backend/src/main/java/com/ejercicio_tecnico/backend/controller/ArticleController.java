package com.ejercicio_tecnico.backend.controller;

import com.ejercicio_tecnico.backend.dto.ArticleDto;
import com.ejercicio_tecnico.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/create-article")
    public ResponseEntity<ArticleDto> crearArticulo(@RequestBody ArticleDto articleDto) {
        ArticleDto nuevoArticulo = articleService.createArticle(articleDto);
        return new ResponseEntity<>(nuevoArticulo, HttpStatus.CREATED);
    }

    @PutMapping("/update-article")
    public ResponseEntity<ArticleDto> actualizarArticulo(@RequestBody ArticleDto articleDto) {
        ArticleDto articuloActualizado = articleService.updateArticle(articleDto);
        return new ResponseEntity<>(articuloActualizado, HttpStatus.OK);
    }

    @GetMapping("/getById-article/{id}")
    public ResponseEntity<ArticleDto> obtenerArticuloPorId(@PathVariable Long id) {
        ArticleDto articulo = articleService.getByIdArticle(id);
        return new ResponseEntity<>(articulo, HttpStatus.OK);
    }

    @GetMapping("/list-articles")
    public ResponseEntity<List<ArticleDto>> listarArticulos() {
        List<ArticleDto> articulos = articleService.listArticle();
        return new ResponseEntity<>(articulos, HttpStatus.OK);
    }

    @DeleteMapping("/delete-article/{id}")
    public ResponseEntity<Void> eliminarArticulo(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
