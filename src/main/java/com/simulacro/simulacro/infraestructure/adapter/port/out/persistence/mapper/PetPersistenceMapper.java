package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper;

import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.PetEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetPersistenceMapper {

    /**
     * Convierte Pet (dominio) → PetEntity (JPA)
     */
    public PetEntity toEntity(Pet domain) {
        if (domain == null) {
            return null;
        }

        PetEntity entity = new PetEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setName(domain.getName());
        entity.setSpecies(domain.getSpecies());
        entity.setBreed(domain.getBreed());
        entity.setAge(domain.getAge());
        entity.setOwnerId(Long.valueOf(domain.getOwner()));
        entity.setOwnerDocument(domain.getOwnerDocument());
        entity.setStatus(domain.getStatus());
        return entity;
    }

    /**
     * Convierte PetEntity (JPA) → Pet (dominio)
     */
    public Pet toDomain(PetEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Pet(
                entity.getId(),
                entity.getName(),
                entity.getSpecies(),
                entity.getBreed(),
                entity.getAge(),
                entity.getOwnerName(),
                entity.getOwnerDocument(),
                entity.getStatus()
        );
    }

    /**
     * Convierte lista de entities → lista de domain
     */
    public List<Pet> toDomainList(List<PetEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}