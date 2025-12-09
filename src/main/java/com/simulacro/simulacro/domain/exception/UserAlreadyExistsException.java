package com.simulacro.simulacro.domain.exception;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un nombre de usuario que ya existe.
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
