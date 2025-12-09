package com.simulacro.simulacro.domain.port.in.veterinarian;

import com.simulacro.simulacro.domain.model.veterinarian.Vet;

public interface UpdateVetUseCase {
    Vet updateVet(Long id, String name, String specialty);
}
