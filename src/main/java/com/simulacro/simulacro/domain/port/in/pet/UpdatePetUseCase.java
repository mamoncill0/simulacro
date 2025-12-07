package com.simulacro.simulacro.domain.port.in.pet;

import com.simulacro.simulacro.domain.model.pets.Pet;

public interface UpdatePetUseCase {
    Pet updatePet(Long petId, String name, String breed, Integer age);
}