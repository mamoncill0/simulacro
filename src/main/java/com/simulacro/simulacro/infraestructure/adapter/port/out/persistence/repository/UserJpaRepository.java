package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository;

import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interfaz de repositorio de Spring Data JPA para la entidad UserEntity.
 * Proporciona métodos CRUD básicos y consultas personalizadas para la gestión de usuarios.
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Busca un UserEntity por su nombre de usuario.
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el UserEntity si se encuentra, o vacío si no.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Verifica si un UserEntity con el nombre de usuario dado existe.
     * @param username El nombre de usuario a verificar.
     * @return true si existe un usuario con ese nombre de usuario, false en caso contrario.
     */
    boolean existsByUsername(String username);
}
