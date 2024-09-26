package com.ejercicio_tecnico.backend.controller;

import com.ejercicio_tecnico.backend.exception.ArticuloNotFoundException;
import com.ejercicio_tecnico.backend.exception.ClienteNotFoundException;
import com.ejercicio_tecnico.backend.exception.OrdenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handleClienteNotFoundException(ClienteNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrdenNotFoundException.class)
    public ResponseEntity<String> handleOrdenNotFoundException(OrdenNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticuloNotFoundException.class)
    public ResponseEntity<String> handleArticuloNotFoundException(ArticuloNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

