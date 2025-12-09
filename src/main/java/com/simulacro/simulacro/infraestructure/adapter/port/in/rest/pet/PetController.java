package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet;

import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.port.in.pet.DeletePetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.FindPetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.RegisterPetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.UpdatePetUseCase;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.dto.request.RegisterPetRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.dto.response.PetResponse;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.mapper.PetDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importar PreAuthorize
import org.springframework.security.core.Authentication; // Importar Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Importar SecurityContextHolder
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    private final RegisterPetUseCase registerPetUseCase;
    // ... otros casos de uso
    private final FindPetUseCase findPetUseCase;
    private final UpdatePetUseCase updatePetUseCase;
    private final DeletePetUseCase deletePetUseCase; // Corregido el nombre de la variable
    private final PetDtoMapper petDtoMapper;

    public PetController(RegisterPetUseCase registerPetUseCase, FindPetUseCase findPetUseCase,
                         UpdatePetUseCase updatePetUseCase, DeletePetUseCase deletePetUseCase,
                         PetDtoMapper petDtoMapper) {
        this.registerPetUseCase = registerPetUseCase;
        this.findPetUseCase = findPetUseCase;
        this.updatePetUseCase = updatePetUseCase;
        this.deletePetUseCase = deletePetUseCase;
        this.petDtoMapper = petDtoMapper;
    }

    /**
     * Registra una nueva mascota. Solo ADMIN puede registrar mascotas directamente.
     * Los DUEÑOS registran mascotas a través de su propio proceso de registro de usuario.
     * @param request Datos de la mascota a registrar.
     * @return La mascota registrada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede registrar mascotas
    public ResponseEntity<PetResponse> registerPet(@Valid @RequestBody RegisterPetRequest request) {
        Pet registeredPet = registerPetUseCase.registerPet(
                request.getPetName(),
                request.getSpecies(),
                request.getBreed(),
                request.getAge(),
                request.getOwnerName(),
                request.getOwnerDocument()
        );
        return ResponseEntity.status(201).body(petDtoMapper.toResponse(registeredPet));
    }

    /**
     * Obtiene una mascota por su ID.
     * ADMIN y VETERINARIO pueden ver cualquier mascota.
     * DUEÑO solo puede ver sus propias mascotas.
     * @param id ID de la mascota.
     * @return La mascota encontrada.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VETERINARIO') or (hasRole('ROLE_DUEÑO') and @petSecurity.isOwner(#id))")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        return findPetUseCase.findById(id)
                .map(pet -> ResponseEntity.ok(petDtoMapper.toResponse(pet)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza los datos de una mascota.
     * ADMIN puede actualizar cualquier mascota.
     * DUEÑO solo puede actualizar sus propias mascotas.
     * @param id ID de la mascota a actualizar.
     * @param request Datos actualizados de la mascota.
     * @return La mascota actualizada.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_DUEÑO') and @petSecurity.isOwner(#id))")
    public ResponseEntity<PetResponse> updatePet(@PathVariable Long id, @Valid @RequestBody RegisterPetRequest request) {
        Pet updatedPet = updatePetUseCase.updatePet(
                id,
                request.getPetName(),
                request.getBreed(),
                request.getAge()
        );
        return ResponseEntity.ok(petDtoMapper.toResponse(updatedPet));
    }

    /**
     * Desactiva una mascota.
     * ADMIN puede desactivar cualquier mascota.
     * DUEÑO solo puede desactivar sus propias mascotas.
     * @param id ID de la mascota a desactivar.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_DUEÑO') and @petSecurity.isOwner(#id))")
    public ResponseEntity<Void> deactivatePet(@PathVariable Long id) {
        deletePetUseCase.deactivatePet(id);
        return ResponseEntity.noContent().build();
    }
}
