package com.ejercicio_tecnico.backend.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderArticleDto {
    private Long id;
    private int cantidad;
    private String name;
}
