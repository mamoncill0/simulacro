package com.simulacro.simulacro.domain.port.in.auth;

import com.simulacro.simulacro.domain.model.user.Role;
import com.simulacro.simulacro.domain.model.user.User;

/**
 * Interfaz para el caso de uso de registro de un nuevo usuario.
 * Define la operación que permite crear una nueva cuenta de usuario en el sistema.
 */
public interface RegisterUserUseCase {
    /**
     * Registra un nuevo usuario en el sistema.
     * @param username El nombre de usuario deseado.
     * @param password La contraseña del usuario.
     * @param role El rol asignado al usuario (ej. DUEÑO, VETERINARIO, ADMIN).
     * @param ownerName Nombre del dueño (opcional, solo para ROLE_DUEÑO).
     * @param ownerDocument Documento del dueño (opcional, solo para ROLE_DUEÑO).
     * @return El objeto User registrado.
     */
    User registerUser(String username, String password, Role role, String ownerName, String ownerDocument);
}
