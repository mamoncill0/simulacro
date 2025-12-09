package com.simulacro.simulacro.domain.exception;

/**
 * Excepción lanzada cuando las credenciales de autenticación (usuario/contraseña) son inválidas.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
