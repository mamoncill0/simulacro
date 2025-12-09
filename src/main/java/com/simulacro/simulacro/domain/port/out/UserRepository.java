package com.simulacro.simulacro.domain.port.out;

import com.simulacro.simulacro.domain.model.user.User;

import java.util.Optional;

/**
 * Puerto de salida para las operaciones de persistencia de usuarios.
 * Define la interfaz que la capa de infraestructura debe implementar para interactuar con los datos de usuario.
 */
public interface UserRepository {
    /**
     * Guarda un usuario en la base de datos.
     * @param user El objeto User del dominio a guardar.
     * @return El User guardado, posiblemente con el ID generado.
     */
    User save(User user);

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el User si se encuentra, o vacío si no.
     */
    Optional<User> findByUsername(String username);

    /**
     * Verifica si un nombre de usuario ya existe en la base de datos.
     * @param username El nombre de usuario a verificar.
     * @return true si el nombre de usuario ya existe, false en caso contrario.
     */
    boolean existsByUsername(String username);
}
