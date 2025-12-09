package com.simulacro.simulacro.domain.port.in.owner;

import com.simulacro.simulacro.domain.model.owner.OwnerPet;

import java.util.List;
import java.util.Optional;

public interface FindOwnerUseCase {
    Optional<OwnerPet> findById(Long id);
    Optional<OwnerPet> findByDocument(String document);
    List<OwnerPet> findAll();
}
