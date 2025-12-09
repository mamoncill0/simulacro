package com.simulacro.simulacro.domain.exception;

/**
 * Excepción lanzada cuando un usuario no puede ser encontrado en el sistema.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
