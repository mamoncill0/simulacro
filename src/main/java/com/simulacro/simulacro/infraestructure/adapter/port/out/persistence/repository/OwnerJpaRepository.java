package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository;

import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {
    Optional<OwnerEntity> findByDocument(String document);
    boolean existsByDocument(String document);
}