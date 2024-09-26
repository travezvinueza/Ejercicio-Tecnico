package com.ejercicio_tecnico.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDto {
    private Long id;
    private String codigo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private Long clienteId;
    private List<ArticuloDto> articulos;
}
