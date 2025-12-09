package com.simulacro.simulacro.domain.port.in.veterinarian;

import com.simulacro.simulacro.domain.model.veterinarian.Vet;

public interface RegisterVetUseCase {
    Vet registerVet(String name, String specialty);
}
