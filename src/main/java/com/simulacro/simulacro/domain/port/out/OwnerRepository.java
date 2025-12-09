package com.simulacro.simulacro.domain.port.out;

import com.simulacro.simulacro.domain.model.owner.OwnerPet;

import java.util.List; // Importar List
import java.util.Optional;

public interface OwnerRepository {
    OwnerPet save(OwnerPet owner);
    Optional<OwnerPet> findById(Long ownerId);
    Optional<OwnerPet> findByDocument(String document);
    List<OwnerPet> findAll(); // Añadido
    boolean existsByDocument(String document);
}
