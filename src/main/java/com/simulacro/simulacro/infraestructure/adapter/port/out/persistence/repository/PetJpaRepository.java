package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository;
import com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * - Una INTERFAZ específica de Spring Data
 * - Extiende JpaRepository<Entity, ID>
 *                  ¿PARA QUÉ SIRVE?
 * - Spring Data genera automáticamente la implementación
 * - Proporciona métodos CRUD gratis: save(), findById(), etc.
 * - Interactúa directamente con la BASE DE DATOS
 *                    Caracteristicas
 *  * - Usa ENTIDADES de JPA (PetEntity, no Pet)
 * - Spring genera el código SQL automáticamente
 * - NO escribes implementación, Spring lo hace.
 */

public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {

    // Métodos definidos en términos de JPA
    List<PetEntity> findByOwnerId(Long ownerId);  // ← Devuelve PetEntity (JPA)

    // Spring Data genera automáticamente:
    // - save(PetEntity entity)
    // - findById(Long id)
    // - findAll()
    // - delete(PetEntity entity)
    // Y muchos más...}
}