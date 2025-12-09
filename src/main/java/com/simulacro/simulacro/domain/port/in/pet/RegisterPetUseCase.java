package com.simulacro.simulacro.domain.port.in.pet;

import com.simulacro.simulacro.domain.model.pets.Pet;

public interface RegisterPetUseCase {
    Pet registerPet(String petName, String species, String breed,
                    Integer age, String ownerName, String ownerDocument);
}