package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.impl;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import com.simulacro.simulacro.domain.port.out.AppointmentRepository;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.AppointmentEntity;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper.AppointmentPersistenceMapper;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository.AppointmentJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final AppointmentJpaRepository jpaRepository;
    private final AppointmentPersistenceMapper mapper;

    public AppointmentRepositoryImpl(AppointmentJpaRepository jpaRepository,
                                     AppointmentPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = mapper.toEntity(appointment);
        AppointmentEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        return jpaRepository.findById(appointmentId).map(mapper::toDomain);
    }

    @Override
    public List<Appointment> findAll() { // Implementación de findAll()
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByPetId(Long petId) {
        return jpaRepository.findByPetId(petId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByVeterinarianId(Long veterinarianId) {
        return jpaRepository.findByVeterinarianId(veterinarianId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDate(LocalDate date) {
        return jpaRepository.findByDate(date).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByVeterinarianIdAndDateAndTime(Long veterinarianId, LocalDate date, LocalTime time) {
        // Delegación corregida para usar el nombre de método actualizado en JpaRepository
        return jpaRepository.existsByVeterinarianIdAndDateAndHour(veterinarianId, date, time);
    }

    @Override
    public void delete(Long appointmentId) {
        jpaRepository.deleteById(appointmentId);
    }
}
