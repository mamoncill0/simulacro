package com.simulacro.simulacro.infraestructure.config.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Implementación personalizada de UserDetails de Spring Security.
 * Extiende la clase User de Spring Security para incluir el userId y ownerPetId,
 * permitiendo un control de acceso más granular para los usuarios.
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long userId; // Añadido: ID del usuario
    private final Long ownerPetId;

    /**
     * Constructor para crear una instancia de CustomUserDetails.
     * @param username El nombre de usuario.
     * @param password La contraseña (encriptada).
     * @param authorities La colección de GrantedAuthority (roles) del usuario.
     * @param userId El ID del usuario en la base de datos.
     * @param ownerPetId El ID del OwnerPet asociado al usuario (puede ser null).
     */
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, Long ownerPetId) {
        super(username, password, authorities);
        this.userId = userId;
        this.ownerPetId = ownerPetId;
    }

    /**
     * Obtiene el ID del usuario.
     * @return El ID del usuario.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Obtiene el ID del OwnerPet asociado a este usuario.
     * @return El ID del OwnerPet, o null si no es un usuario DUEÑO.
     */
    public Long getOwnerPetId() {
        return ownerPetId;
    }
}
