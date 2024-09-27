package com.ejercicio_tecnico.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String code;
    private String name;
    private BigDecimal unitPrice;
}
