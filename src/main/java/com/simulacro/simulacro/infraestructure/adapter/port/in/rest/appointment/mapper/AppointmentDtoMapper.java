package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.mapper;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.response.AppointmentResponse;
import org.springframework.stereotype.Component;

@Component
public class AppointmentDtoMapper {

    public AppointmentResponse toResponse(Appointment domain) {
        if (domain == null) {
            return null;
        }

        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(domain.getAppointmentId());
        response.setPetId(domain.getPetId());
        response.setVetId(domain.getVetId());
        response.setDate(domain.getDate());
        response.setHour(domain.getHour());
        response.setReason(domain.getReason());
        response.setStatus(domain.getStatus());
        response.setDiagnosis(domain.getDiagnosis());

        return response;
    }
}
