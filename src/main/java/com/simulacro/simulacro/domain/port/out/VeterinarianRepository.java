package com.simulacro.simulacro.domain.port.out;

import com.simulacro.simulacro.domain.model.veterinarian.Vet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface VeterinarianRepository {
    Vet save(Vet vet);
    Optional<Vet> findById(Long id);
    List<Vet> findAll();
    void delete(Long id);
    boolean isAvailable(Long id, LocalDate date, LocalTime time); // Cambiado veterinarianId a id
    boolean existsById(Long id); // Cambiado veterinarianId a id
}
