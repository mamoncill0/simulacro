package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.impl;

import com.simulacro.simulacro.domain.model.veterinarian.Vet;
import com.simulacro.simulacro.domain.port.out.VeterinarianRepository;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.VeterinarianEntity;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper.VeterinarianPersistenceMapper;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository.VeterinarianJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class VeterinarianRepositoryImpl implements VeterinarianRepository {

    private final VeterinarianJpaRepository jpaRepository;
    private final VeterinarianPersistenceMapper mapper;

    public VeterinarianRepositoryImpl(VeterinarianJpaRepository jpaRepository,
                                      VeterinarianPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Vet save(Vet vet) {
        VeterinarianEntity entity = mapper.toEntity(vet);
        VeterinarianEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Vet> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Vet> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean isAvailable(Long id, LocalDate date, LocalTime time) {
        // Esta lógica es más compleja y probablemente requeriría una consulta
        // en el AppointmentJpaRepository para verificar si el veterinario
        // tiene una cita a esa hora y fecha.
        // Por ahora, solo verificaremos que el veterinario exista.
        // La lógica real de disponibilidad debería estar en el AppointmentService
        // o en un servicio de dominio de disponibilidad.
        return jpaRepository.existsById(id); // Implementación placeholder
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
