package com.ejercicio_tecnico.backend.exception;

public class OrdenNotFoundException extends RuntimeException {
    public OrdenNotFoundException(String message) {
        super(message);
    }
}
