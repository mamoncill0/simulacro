package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper;

import com.simulacro.simulacro.domain.model.user.User;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para la conversión entre el modelo de dominio {@link User}
 * y la entidad de persistencia {@link UserEntity}.
 */
@Component
public class UserPersistenceMapper {

    /**
     * Convierte un objeto User del dominio a un UserEntity para su persistencia.
     * @param domain El objeto User del dominio.
     * @return La UserEntity correspondiente.
     */
    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setPassword(domain.getPassword()); // La contraseña ya debería estar encriptada en el dominio
        entity.setRole(domain.getRole());
        entity.setOwnerPetId(domain.getOwnerPetId()); // Añadido: mapeo de ownerPetId
        return entity;
    }

    /**
     * Convierte un UserEntity de la capa de persistencia a un objeto User del dominio.
     * @param entity La UserEntity recuperada de la base de datos.
     * @return El objeto User del dominio correspondiente.
     */
    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole(),
                entity.getOwnerPetId() // Añadido: mapeo de ownerPetId
        );
    }
}
