package com.simulacro.simulacro.domain.port.out;

import com.simulacro.simulacro.domain.model.pets.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet save(Pet pet);
    Optional<Pet> findById(Long petId);
    List<Pet> findAll();
    List<Pet> findByOwnerId(Long ownerId);
    void delete(Long petId);
    boolean existsById(Long petId);
}