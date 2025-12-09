package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.impl;

import com.simulacro.simulacro.domain.model.user.User;
import com.simulacro.simulacro.domain.port.out.UserRepository;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.UserEntity;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper.UserPersistenceMapper;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementación del puerto de salida {@link UserRepository}.
 * Adapta las operaciones de persistencia de usuarios utilizando Spring Data JPA.
 */
@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper mapper;

    /**
     * Constructor para inyectar las dependencias necesarias.
     * @param jpaRepository Repositorio JPA para UserEntity.
     * @param mapper Mapper para convertir entre User y UserEntity.
     */
    public UserRepositoryImpl(UserJpaRepository jpaRepository, UserPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    /**
     * Guarda un usuario en la base de datos.
     * Convierte el User del dominio a UserEntity, lo guarda y lo convierte de nuevo a User.
     * @param user El objeto User del dominio a guardar.
     * @return El User guardado.
     */
    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el User si se encuentra, o vacío si no.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(mapper::toDomain);
    }

    /**
     * Verifica si un nombre de usuario ya existe en la base de datos.
     * @param username El nombre de usuario a verificar.
     * @return true si el nombre de usuario ya existe, false en caso contrario.
     */
    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}
