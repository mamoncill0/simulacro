package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.response;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        LocalDateTime date,
        Long petId,
        String description
) {}

