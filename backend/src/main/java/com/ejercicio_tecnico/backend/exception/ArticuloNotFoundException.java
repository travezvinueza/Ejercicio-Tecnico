package com.ejercicio_tecnico.backend.exception;

public class ArticuloNotFoundException extends RuntimeException {
    public ArticuloNotFoundException(String message) {
        super(message);
    }
}
