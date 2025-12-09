package com.simulacro.simulacro.domain.port.in.auth;

/**
 * Interfaz para el caso de uso de autenticación de un usuario.
 * Define la operación que permite a un usuario iniciar sesión y obtener un token de autenticación.
 */
public interface AuthenticateUserUseCase {
    /**
     * Autentica a un usuario con sus credenciales y genera un token JWT.
     * @param username El nombre de usuario.
     * @param password La contraseña.
     * @return El token JWT si la autenticación es exitosa.
     * @throws RuntimeException si las credenciales son inválidas.
     */
    String authenticate(String username, String password);
}
