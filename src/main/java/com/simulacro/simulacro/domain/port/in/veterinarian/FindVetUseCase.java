package com.simulacro.simulacro.domain.port.in.veterinarian;

import com.simulacro.simulacro.domain.model.veterinarian.Vet;

import java.util.List;
import java.util.Optional;

public interface FindVetUseCase {
    Optional<Vet> findById(Long id);
    List<Vet> findAll();
}
