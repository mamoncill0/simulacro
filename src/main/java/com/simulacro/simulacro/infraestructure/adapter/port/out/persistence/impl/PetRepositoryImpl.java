package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.impl;

import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.port.out.PetRepository;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.PetEntity;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper.PetPersistenceMapper;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository.PetJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
                ¿PARA QUÉ SIRVE?
 * - Traduce entre Pet (dominio) y PetEntity (JPA)
 * - Hace de PUENTE entre ambos mundos
 * - Implementa la lógica de persistencia

                  RESPONSABILIDADES:
 * 1. Convertir Pet → PetEntity (para guardar en BD)
 * 2. Convertir PetEntity → Pet (para devolver al dominio)
 * 3. Delegar operaciones a PetJpaRepository
 */



@Component  // ← Spring crea una instancia automáticamente
public class PetRepositoryImpl implements PetRepository {  // ← Implementa el puerto del dominio

    private final PetJpaRepository jpaRepository;  // ← Usa el repositorio de Spring Data
    private final PetPersistenceMapper mapper;

    public PetRepositoryImpl(PetJpaRepository jpaRepository, PetPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Pet save(Pet pet) {
        // 1. Convertir Pet (dominio) → PetEntity (JPA)
        PetEntity entity = mapper.toEntity(pet);

        // 2. Guardar en BD usando Spring Data
        PetEntity savedEntity = jpaRepository.save(entity);

        // 3. Convertir PetEntity (JPA) → Pet (dominio)
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Pet> findById(Long petId) {
        // 1. Buscar en BD usando Spring Data (devuelve PetEntity)
        // 2. Convertir a Pet (dominio) si existe
        return jpaRepository.findById(petId)
                .map(this.mapper::toDomain);  // ← Conversión automática
    }

    @Override
    public List<Pet> findAll() {
        return jpaRepository.findAll().stream()
                .map(this.mapper::toDomain)  // ← Convertir cada PetEntity a Pet
                .collect(Collectors.toList());
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        return jpaRepository.findByOwnerId(ownerId).stream()
                .map(this.mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long petId) {
        jpaRepository.deleteById(petId);
    }

    @Override
    public boolean existsById(Long petId) {
        return jpaRepository.existsById(petId);
    }
}