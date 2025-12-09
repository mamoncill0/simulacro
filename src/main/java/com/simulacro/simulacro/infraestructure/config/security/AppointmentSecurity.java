package com.simulacro.simulacro.infraestructure.config.security;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.port.in.appointment.FindAppointmentUseCase;
import com.simulacro.simulacro.domain.port.in.pet.FindPetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Componente de seguridad para verificar la propiedad o asignación de citas.
 * Utilizado en expresiones @PreAuthorize para controlar el acceso de DUEÑOS y VETERINARIOS.
 */
@Component("appointmentSecurity") // Nombre del bean para usar en @PreAuthorize
@RequiredArgsConstructor
public class AppointmentSecurity {

    private final FindAppointmentUseCase findAppointmentUseCase;
    private final FindPetUseCase findPetUseCase; // Para verificar el dueño de la mascota de la cita

    /**
     * Verifica si el usuario autenticado es el dueño de la mascota asociada a la cita.
     * @param appointmentId El ID de la cita.
     * @return true si el usuario autenticado es el dueño de la mascota de la cita, false en caso contrario.
     */
    public boolean isOwnerOfAppointment(Long appointmentId) {
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
            return false; // El usuario no tiene un ownerPetId asociado
        }

        Optional<Appointment> appointmentOptional = findAppointmentUseCase.findById(appointmentId);
        if (appointmentOptional.isEmpty()) {
            return false; // La cita no existe
        }

        Long petId = appointmentOptional.get().getPetId();
        Optional<Pet> petOptional = findPetUseCase.findById(petId);

        return petOptional.map(pet -> pet.getOwnerId().equals(currentOwnerPetId)).orElse(false);
    }

    /**
     * Verifica si el usuario autenticado (VETERINARIO) está asignado a la cita.
     * @param appointmentId El ID de la cita.
     * @return true si el veterinario autenticado está asignado a la cita, false en caso contrario.
     */
    public boolean isAssignedToVet(Long appointmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            return false;
        }

        CustomUserDetails currentUser = (CustomUserDetails) principal;
        // Asumimos que el ID del veterinario autenticado es el ID del usuario
        Long currentVetId = currentUser.getUserId(); // Corregido: Usar getUserId()

        if (currentVetId == null) {
            return false; // El usuario no tiene un ID asociado
        }

        Optional<Appointment> appointmentOptional = findAppointmentUseCase.findById(appointmentId);
        return appointmentOptional.map(appointment -> appointment.getVetId().equals(currentVetId)).orElse(false);
    }
}
