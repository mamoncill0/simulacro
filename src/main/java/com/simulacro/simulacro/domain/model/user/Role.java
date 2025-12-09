package com.simulacro.simulacro.domain.model.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum que define los roles de usuario en la aplicación.
 * Cada rol tiene un nombre que se utilizará en Spring Security para la autorización.
 * Implementa GrantedAuthority para integrarse con Spring Security.
 */
public enum Role implements GrantedAuthority {
    ROLE_DUEÑO,
    ROLE_VETERINARIO,
    ROLE_ADMIN;

    /**
     * Devuelve el nombre completo del rol (ej. "ROLE_ADMIN").
     * Este método es requerido por la interfaz GrantedAuthority.
     * @return El nombre del rol como String.
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
