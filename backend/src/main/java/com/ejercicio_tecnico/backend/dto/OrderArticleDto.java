package com.ejercicio_tecnico.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderArticleDto {
    private Long articleId;
    private String articleName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}

