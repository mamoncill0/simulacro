package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.mapper;

import com.simulacro.simulacro.domain.model.veterinarian.Vet;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.response.VetResponse;
import org.springframework.stereotype.Component;

@Component
public class VetDtoMapper {

    public VetResponse toResponse(Vet domain) {
        if (domain == null) {
            return null;
        }

        VetResponse response = new VetResponse();
        response.setId(domain.getId()); // Usar el nuevo getter getId()
        response.setName(domain.getName()); // Usar el nuevo getter getName()
        response.setSpecialty(domain.getSpecialty()); // Añadir mapeo para specialty

        return response;
    }
}
