package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository;

import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.VeterinarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeterinarianJpaRepository extends JpaRepository<VeterinarianEntity, Long> {
    // Métodos declarados explícitamente para mayor claridad, aunque JpaRepository ya los provee
    List<VeterinarianEntity> findAll();
    Optional<VeterinarianEntity> findById(Long id);
    <S extends VeterinarianEntity> S save(S entity);
    void deleteById(Long id);

    // Método para verificar existencia, ya que se usa en AppointmentService
    boolean existsById(Long id);
}
