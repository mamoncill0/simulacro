package com.simulacro.simulacro.domain.port.out;

import java.time.LocalDate;
import java.time.LocalTime;

public interface VeterinarianRepository {
    boolean isAvailable(Long veterinarianId, LocalDate date, LocalTime time);
    boolean existsById(Long veterinarianId);
}