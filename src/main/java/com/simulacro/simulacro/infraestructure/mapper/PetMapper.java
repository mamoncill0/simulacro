package com.simulacro.simulacro.infraestructure.mapper;

import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct genera automáticamente la implementación
 */
@Mapper(componentModel = "spring")  // ← Spring lo registra como @Component
public interface PetMapper {

    // MapStruct genera el código automáticamente
    PetEntity toEntity(Pet domain);

    Pet toDomain(PetEntity entity);

    // Si los nombres no coinciden, puedes mapearlos
    @Mapping(source = "ownerId", target = "ownerId")
    @Mapping(source = "ownerName", target = "ownerName")
    PetEntity toEntityCustom(Pet domain);
}
