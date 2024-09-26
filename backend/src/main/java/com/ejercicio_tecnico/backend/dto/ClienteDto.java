package com.ejercicio_tecnico.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private Long id;
    private String nombre;
    private String apellido;
    private List<OrdenDto> ordenes;
}
