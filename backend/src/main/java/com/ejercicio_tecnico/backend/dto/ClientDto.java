package com.ejercicio_tecnico.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private String lastname;
    private List<OrderDto> orders;
}
