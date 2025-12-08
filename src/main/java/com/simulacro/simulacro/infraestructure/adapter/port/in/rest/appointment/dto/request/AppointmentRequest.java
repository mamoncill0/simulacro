package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.request;

import java.time.LocalDateTime;

public record AppointmentRequest(
        LocalDateTime date,
        Long petId,
        Long veterinarianId,
        String description
) {}
