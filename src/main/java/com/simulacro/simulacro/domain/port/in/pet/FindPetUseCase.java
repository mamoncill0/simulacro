package com.simulacro.simulacro.domain.port.in.pet;

import com.simulacro.simulacro.domain.model.pets.Pet;

import java.util.Optional;

public interface FindPetUseCase {
    Optional<Pet> findById(Long petId);
}