package com.simulacro.simulacro.infraestructure.exception;

import com.simulacro.simulacro.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manejador global de excepciones para la aplicación.
 * Centraliza la gestión de errores y devuelve respuestas estandarizadas en formato ProblemDetail (RFC 7807).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de usuario no encontrado.
     * @param ex La excepción UserNotFoundException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 404 Not Found.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso no encontrado");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/not-found"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Maneja excepciones de usuario ya existente.
     * @param ex La excepción UserAlreadyExistsException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 409 Conflict.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflicto de datos");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/user-already-exists"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Maneja excepciones de credenciales inválidas.
     * @param ex La excepción InvalidCredentialsException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 401 Unauthorized.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Credenciales inválidas");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/invalid-credentials"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Maneja excepciones de argumentos de método no válidos (Bean Validation).
     * @param ex La excepción MethodArgumentNotValidException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 400 Bad Request y detalles de los errores de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación en la petición.");
        problemDetail.setTitle("Argumentos inválidos");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/invalid-arguments"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    /**
     * Maneja excepciones de acceso denegado (autorización).
     * @param ex La excepción AccessDeniedException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 403 Forbidden.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Acceso denegado. No tiene permisos para realizar esta acción.");
        problemDetail.setTitle("Acceso denegado");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/access-denied"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Maneja excepciones de autenticación (ej. token JWT inválido o expirado).
     * @param ex La excepción AuthenticationException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 401 Unauthorized.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Fallo de autenticación");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/authentication-failed"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Maneja excepciones de violación de integridad de datos (ej. clave única duplicada).
     * @param ex La excepción DataIntegrityViolationException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 409 Conflict.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Violación de integridad de datos. Posiblemente un registro duplicado o una referencia inválida.");
        problemDetail.setTitle("Conflicto de datos");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/data-integrity-violation"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Maneja excepciones de argumentos ilegales.
     * @param ex La excepción IllegalArgumentException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 400 Bad Request.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Argumento inválido");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/illegal-argument"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Maneja excepciones de estado ilegal.
     * @param ex La excepción IllegalStateException.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 409 Conflict.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Estado inválido");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/illegal-state"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }

    /**
     * Manejador genérico para cualquier otra excepción no capturada.
     * @param ex La excepción genérica.
     * @param request La petición HTTP.
     * @return Un ProblemDetail con estado 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado.");
        problemDetail.setTitle("Error interno del servidor");
        problemDetail.setType(URI.create("https://api.simulacro.com/errors/internal-server-error"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        // Opcional: Loggear la excepción completa para depuración
        // log.error("Error inesperado: {}", ex.getMessage(), ex);
        return problemDetail;
    }
}
