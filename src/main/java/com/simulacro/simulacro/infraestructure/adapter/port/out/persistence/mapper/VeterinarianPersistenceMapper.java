package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper;

import com.simulacro.simulacro.domain.model.veterinarian.Vet;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.VeterinarianEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VeterinarianPersistenceMapper {

    public VeterinarianEntity toEntity(Vet domain) {
        if (domain == null) return null;

        VeterinarianEntity entity = new VeterinarianEntity();
        entity.setId(domain.getId()); // Mapear el ID
        entity.setName(domain.getName()); // Mapear el nombre
        entity.setSpecialty(domain.getSpecialty()); // Mapear la especialidad
        return entity;
    }

    public Vet toDomain(VeterinarianEntity entity) {
        if (entity == null) return null;
        return new Vet(
                entity.getId(), // Mapear el ID
                entity.getName(), // Mapear el nombre
                entity.getSpecialty() // Mapear la especialidad
        );
    }

    public List<Vet> toDomainList(List<VeterinarianEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
