package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository;

import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findAll(); // Añadido
    List<AppointmentEntity> findByPetId(Long petId);
    List<AppointmentEntity> findByVeterinarianId(Long veterinarianId);
    List<AppointmentEntity> findByDate(LocalDate date);
    // Corregido: 'time' a 'hour' para que coincida con el campo en AppointmentEntity
    boolean existsByVeterinarianIdAndDateAndHour(Long veterinarianId, LocalDate date, LocalTime hour);
}
