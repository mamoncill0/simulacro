package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.owner.mapper;

import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.owner.dto.response.OwnerResponse;
import org.springframework.stereotype.Component;

@Component
public class OwnerDtoMapper {

    public OwnerResponse toResponse(OwnerPet domain) {
        if (domain == null) {
            return null;
        }

        OwnerResponse response = new OwnerResponse();
        response.setId(domain.getId());
        response.setName(domain.getName());
        response.setDocument(domain.getDocument());

        return response;
    }
}
