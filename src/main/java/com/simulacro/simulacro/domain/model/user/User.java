package com.simulacro.simulacro.domain.model.user;

import java.util.Objects;

/**
 * Modelo de dominio para un usuario.
 * Contiene la lógica de negocio y validaciones relacionadas con el usuario.
 */
public class User {
    private Long id;
    private String username;
    private String password; // La contraseña se almacenará encriptada
    private Role role;
    private Long ownerPetId; // Añadido: ID del OwnerPet asociado a este usuario (si el rol es DUEÑO)

    // Constructor vacío requerido por algunas librerías (ej. mappers)
    public User() {
    }

    /**
     * Constructor para crear un nuevo usuario (sin ID, se genera en persistencia).
     * Realiza validaciones básicas de los atributos.
     * @param username Nombre de usuario.
     * @param password Contraseña (se espera que ya esté encriptada o se encriptará antes de persistir).
     * @param role Rol del usuario.
     * @param ownerPetId ID del OwnerPet asociado (puede ser null si el rol no es DUEÑO).
     */
    public User(String username, String password, Role role, Long ownerPetId) {
        validateUsername(username);
        validatePassword(password);
        validateRole(role);
        this.username = username;
        this.password = password;
        this.role = role;
        this.ownerPetId = ownerPetId;
    }

    /**
     * Constructor completo para reconstruir un usuario desde la capa de persistencia.
     * @param id ID único del usuario.
     * @param username Nombre de usuario.
     * @param password Contraseña (se espera que ya esté encriptada).
     * @param role Rol del usuario.
     * @param ownerPetId ID del OwnerPet asociado (puede ser null si el rol no es DUEÑO).
     */
    public User(Long id, String username, String password, Role role, Long ownerPetId) {
        this(username, password, role, ownerPetId); // Reutiliza las validaciones del constructor sin ID
        this.id = id;
    }

    // ========== REGLAS DE NEGOCIO / VALIDACIONES ==========

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("El nombre de usuario debe tener entre 3 y 50 caracteres.");
        }
        // Podrían añadirse más validaciones (ej. formato de email, caracteres permitidos)
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        // Nota: Las validaciones de complejidad de contraseña (min. mayúsculas, números, etc.)
        // suelen hacerse en la capa de presentación o en un servicio de seguridad antes de llegar aquí.
        // Aquí solo se valida que no esté vacía.
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("El rol del usuario es obligatorio.");
        }
    }

    // ========== GETTERS Y SETTERS ==========

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
        validateUsername(username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        validateRole(role);
        this.role = role;
    }

    public Long getOwnerPetId() {
        return ownerPetId;
    }

    public void setOwnerPetId(Long ownerPetId) {
        this.ownerPetId = ownerPetId;
    }

    // ========== EQUALS Y HASHCODE (para comparaciones de objetos) ==========

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    // ========== TOSTRING ==========

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", ownerPetId=" + ownerPetId +
                '}';
    }
}
