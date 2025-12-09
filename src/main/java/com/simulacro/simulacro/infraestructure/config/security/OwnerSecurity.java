package com.simulacro.simulacro.infraestructure.config.security;

import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Componente de seguridad para verificar la propiedad de los dueños.
 * Utilizado en expresiones @PreAuthorize para controlar el acceso de los DUEÑOS.
 */
@Component("ownerSecurity") // Nombre del bean para usar en @PreAuthorize
@RequiredArgsConstructor
public class OwnerSecurity {

    private final OwnerRepository ownerRepository;

    /**
     * Verifica si el usuario autenticado es el dueño con el ID dado.
     * @param ownerId El ID del dueño.
     * @return true si el usuario autenticado es el dueño, false en caso contrario.
     */
    public boolean isOwner(Long ownerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            return false;
        }

        CustomUserDetails currentUser = (CustomUserDetails) principal;
        Long currentOwnerPetId = currentUser.getOwnerPetId();

        return currentOwnerPetId != null && currentOwnerPetId.equals(ownerId);
    }

    /**
     * Verifica si el usuario autenticado es el dueño con el documento dado.
     * @param document El documento del dueño.
     * @return true si el usuario autenticado es el dueño, false en caso contrario.
     */
    public boolean isOwnerByDocument(String document) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            return false;
        }

        CustomUserDetails currentUser = (CustomUserDetails) principal;
        Long currentOwnerPetId = currentUser.getOwnerPetId();

        if (currentOwnerPetId == null) {
            return false;
        }

        return ownerRepository.findByDocument(document)
                .map(ownerPet -> ownerPet.getId().equals(currentOwnerPetId))
                .orElse(false);
    }
}
