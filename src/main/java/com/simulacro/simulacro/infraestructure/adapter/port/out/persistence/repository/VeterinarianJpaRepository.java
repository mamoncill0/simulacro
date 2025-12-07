package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository;

import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.VeterinarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianJpaRepository extends JpaRepository<VeterinarianEntity, Long> {
    // Spring Data crea automáticamente los métodos básicos
}