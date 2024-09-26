package com.ejercicio_tecnico.backend.repository;

import com.ejercicio_tecnico.backend.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
