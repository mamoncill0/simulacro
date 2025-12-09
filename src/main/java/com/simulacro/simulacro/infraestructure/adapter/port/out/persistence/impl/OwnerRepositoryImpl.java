package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.impl;

import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.OwnerEntity;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.mapper.OwnerPersistenceMapper;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository.OwnerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OwnerRepositoryImpl implements OwnerRepository {

    // Repositorio JPA, hacemos la inyeccion de dependencias
    private final OwnerJpaRepository jpaRepository;
    private final OwnerPersistenceMapper mapper;

    // Constructor para inyectar el repositorio JPA
    public OwnerRepositoryImpl(OwnerJpaRepository jpaRepository, OwnerPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    // Sobreescribimos los metodos de OwnerRepository, le damos la logica de negocio
    @Override
    public OwnerPet save(OwnerPet owner) {
        OwnerEntity entity = mapper.toEntity(owner);
        OwnerEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<OwnerPet> findById(Long ownerId) {
        return jpaRepository.findById(ownerId).map(this.mapper::toDomain);
    }

    @Override
    public Optional<OwnerPet> findByDocument(String document) {
        return jpaRepository.findByDocument(document).map(this.mapper::toDomain);
    }

    @Override
    public List<OwnerPet> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public boolean existsByDocument(String document) {
        return jpaRepository.existsByDocument(document);
    }
}
