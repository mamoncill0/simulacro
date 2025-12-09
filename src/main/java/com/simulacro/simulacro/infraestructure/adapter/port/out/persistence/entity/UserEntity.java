package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity;

import com.simulacro.simulacro.domain.model.user.Role;
import jakarta.persistence.*;

/**
 * Entidad JPA para mapear el modelo de dominio User a la tabla 'users' en la base de datos.
 */
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255) // La contraseña estará encriptada, por eso mayor longitud
    private String password;

    @Enumerated(EnumType.STRING) // Guarda el enum como String en la BD
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "owner_pet_id", nullable = true) // Añadido: ID del OwnerPet asociado, puede ser nulo
    private Long ownerPetId;

    // Constructor vacío requerido por JPA, cambiado a public para permitir la instanciación por mappers
    public UserEntity() {}

    /**
     * Constructor para crear una nueva UserEntity a partir de un nombre de usuario, contraseña, rol y ownerPetId.
     * @param username Nombre de usuario.
     * @param password Contraseña (ya encriptada).
     * @param role Rol del usuario.
     * @param ownerPetId ID del OwnerPet asociado (puede ser null).
     */
    public UserEntity(String username, String password, Role role, Long ownerPetId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.ownerPetId = ownerPetId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getOwnerPetId() {
        return ownerPetId;
    }

    public void setOwnerPetId(Long ownerPetId) {
        this.ownerPetId = ownerPetId;
    }
}
