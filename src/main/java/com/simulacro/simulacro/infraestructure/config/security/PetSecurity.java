package com.simulacro.simulacro.infraestructure.config.security;

import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.port.in.pet.FindPetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Componente de seguridad para verificar la propiedad de las mascotas.
 * Utilizado en expresiones @PreAuthorize para controlar el acceso de los DUEÑOS.
 */
@Component("petSecurity") // Nombre del bean para usar en @PreAuthorize
@RequiredArgsConstructor
public class PetSecurity {

    private final FindPetUseCase findPetUseCase;

    /**
     * Verifica si el usuario autenticado es el dueño de la mascota con el ID dado.
     * @param petId El ID de la mascota.
     * @return true si el usuario autenticado es el dueño, false en caso contrario.
     */
    public boolean isOwner(Long petId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false; // No autenticado
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            return false; // No es un CustomUserDetails, no podemos verificar ownerPetId
        }

        CustomUserDetails currentUser = (CustomUserDetails) principal;
        Long currentOwnerPetId = currentUser.getOwnerPetId();

        if (currentOwnerPetId == null) {
            return false; // El usuario no tiene un ownerPetId asociado (no es DUEÑO o no está configurado)
        }

        Optional<Pet> petOptional = findPetUseCase.findById(petId);
        return petOptional.map(pet -> pet.getOwnerId().equals(currentOwnerPetId)).orElse(false);
    }

    /**
     * Verifica si el usuario autenticado es el dueño del OwnerPet con el ID dado.
     * @param ownerId El ID del OwnerPet.
     * @return true si el usuario autenticado es el dueño, false en caso contrario.
     */
    public boolean isOwnerOfOwnerPet(Long ownerId) {
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
}
