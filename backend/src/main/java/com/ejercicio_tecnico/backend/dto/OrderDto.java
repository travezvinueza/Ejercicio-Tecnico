package com.ejercicio_tecnico.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Long clientId;
    private List<OrderArticleDto> orderArticles;
}
