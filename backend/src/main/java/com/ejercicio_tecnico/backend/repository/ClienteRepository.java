package com.ejercicio_tecnico.backend.repository;

import com.ejercicio_tecnico.backend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNombre(String nombre);
}
