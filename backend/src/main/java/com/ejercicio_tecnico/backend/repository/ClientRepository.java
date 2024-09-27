package com.ejercicio_tecnico.backend.repository;

import com.ejercicio_tecnico.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByName(String name);
}
