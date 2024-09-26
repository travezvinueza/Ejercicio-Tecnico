package com.ejercicio_tecnico.backend.repository;

import com.ejercicio_tecnico.backend.entity.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    boolean existsByNombre(String nombre);
}
