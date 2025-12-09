package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.mapper;

import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.dto.response.PetResponse;
import org.springframework.stereotype.Component;

@Component
public class PetDtoMapper {

    public PetResponse toResponse(Pet domain) {
        if (domain == null) {
            return null;
        }

        PetResponse response = new PetResponse();
        response.setId(domain.getId());
        response.setName(domain.getName());
        response.setSpecies(domain.getSpecies());
        response.setBreed(domain.getBreed());
        response.setAge(domain.getAge());
        response.setOwnerId(domain.getOwnerId());
        response.setOwnerName(domain.getOwnerName());
        response.setOwnerDocument(domain.getOwnerDocument());
        response.setStatus(domain.getStatus());

        return response;
    }
}
