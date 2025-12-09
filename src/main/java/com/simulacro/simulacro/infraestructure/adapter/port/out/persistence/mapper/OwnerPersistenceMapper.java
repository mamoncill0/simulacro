package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper;

import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.OwnerEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OwnerPersistenceMapper {

    //Debe ser public, porque si lo pongo en Private nunca lo podre llamar en otro lado
    public OwnerEntity toEntity(OwnerPet domain) {
        OwnerEntity entity = new OwnerEntity(domain.getName(), domain.getDocument());
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        return entity;
    }

    public OwnerPet toDomain(OwnerEntity entity) {
        return new OwnerPet(entity.getId(), entity.getName(), entity.getDocument());
    }

    public List<OwnerPet> toDomainList(List<OwnerEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
