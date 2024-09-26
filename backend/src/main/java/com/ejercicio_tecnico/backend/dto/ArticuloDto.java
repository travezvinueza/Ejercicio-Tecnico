package com.ejercicio_tecnico.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDto {
    private Long id;
    private String codigo;
    private String nombre;
    private BigDecimal precioUnitario;
}
