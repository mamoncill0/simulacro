package com.simulacro.simulacro.infraestructure.mapper;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.request.AppointmentRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.response.AppointmentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentResponse toResponse(Appointment entity);
    Appointment toEntity(AppointmentRequest request);
}

