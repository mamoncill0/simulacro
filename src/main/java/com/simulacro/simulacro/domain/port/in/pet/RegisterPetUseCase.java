package com.simulacro.simulacro.domain.port.in.pet;

import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.model.pets.Status;

public interface RegisterPetUseCase {
    Pet registerPet(String petName, String species, String breed,
                    Integer age, String ownerName, String ownerDocument, Status status);
}
