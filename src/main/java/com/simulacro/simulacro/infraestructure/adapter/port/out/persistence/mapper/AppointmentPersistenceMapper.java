package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.AppointmentEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentPersistenceMapper {

    public AppointmentEntity toEntity(Appointment domain) {
        if (domain == null) return null;

        AppointmentEntity entity = new AppointmentEntity();
        if (domain.getAppointmentId() != null) {
            entity.setId(domain.getAppointmentId());
        }
        entity.setPetId(domain.getPetId());
        entity.setVeterinarianId(domain.getVetId());
        entity.setDate(domain.getDate());
        entity.setHour(domain.getHour());
        entity.setReason(domain.getReason());
        entity.setStatus(domain.getStatus());
        entity.setDiagnosis(domain.getDiagnosis());
        return entity;
    }

    public Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) return null;
        return new Appointment(
                entity.getId(),
                entity.getPetId(),
                entity.getVeterinarianId(),
                entity.getDate(),
                entity.getHour(),
                entity.getReason(),
                entity.getStatus(),
                entity.getDiagnosis()
        );
    }

    public List<Appointment> toDomainList(List<AppointmentEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
