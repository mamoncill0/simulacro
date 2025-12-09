package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian;


import com.simulacro.simulacro.domain.model.veterinarian.Vet;
import com.simulacro.simulacro.domain.port.in.veterinarian.DeleteVetUseCase;
import com.simulacro.simulacro.domain.port.in.veterinarian.FindVetUseCase;
import com.simulacro.simulacro.domain.port.in.veterinarian.RegisterVetUseCase;
import com.simulacro.simulacro.domain.port.in.veterinarian.UpdateVetUseCase;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.request.RegisterVetRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.request.UpdateVetRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.response.VetResponse;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.mapper.VetDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Esto es un adaptador REST porque recibe peticiones desde el exterior que seria lo que manda el usuario.
Se llama adaptador porque adapta los protocolos HTTP a nuestro codigo Java.
*/


//Haremos directamente en el controller la logica, eso significa que No llamaremos al Service, ya que haremos t0do el trabajo desde aqui, para probar
@RestController
@RequestMapping("/api/v1/veterinarians")
public class VetController {

    private final RegisterVetUseCase registerVetUseCase;
    private final FindVetUseCase findVetUseCase;
    private final UpdateVetUseCase updateVetUseCase;
    private final DeleteVetUseCase deleteVetUseCase;
    private final VetDtoMapper vetDtoMapper;

    public VetController(RegisterVetUseCase registerVetUseCase,
                                  FindVetUseCase findVetUseCase,
                                  UpdateVetUseCase updateVetUseCase,
                                  DeleteVetUseCase deleteVetUseCase,
                                  VetDtoMapper vetDtoMapper) {
        this.registerVetUseCase = registerVetUseCase;
        this.findVetUseCase = findVetUseCase;
        this.updateVetUseCase = updateVetUseCase;
        this.deleteVetUseCase = deleteVetUseCase;
        this.vetDtoMapper = vetDtoMapper;
    }

    /**
     * Registra un nuevo veterinario.
     * Solo ADMIN puede registrar veterinarios.
     * @param request Datos del veterinario a registrar.
     * @return El veterinario registrado.
     */

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede registrar veterinarios
    public ResponseEntity<VetResponse> registerVet(@Valid @RequestBody RegisterVetRequest request) {
        Vet registeredVet = registerVetUseCase.registerVet(
                request.getName(),
                request.getSpecialty()
        );
        return ResponseEntity.status(201).body(vetDtoMapper.toResponse(registeredVet));
    }

    /**
     * Obtiene un veterinario por su ID.
     * ADMIN puede ver cualquier veterinario.
     * VETERINARIO solo puede ver su propio perfil.
     * @param id ID del veterinario.
     * @return El veterinario encontrado.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_VETERINARIO') and principal.userId == #id)")
    public ResponseEntity<VetResponse> getVetById(@PathVariable Long id) {
        return findVetUseCase.findById(id)
                .map(vetDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los veterinarios.
     * ADMIN y VETERINARIO pueden ver todos los veterinarios.
     * @return Lista de todos los veterinarios.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VETERINARIO')") // ADMIN y VETERINARIO pueden ver todos los veterinarios
    public ResponseEntity<List<VetResponse>> getAllVets() {
        List<VetResponse> vets = findVetUseCase.findAll().stream()
                .map(vetDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vets);
    }

    /**
     * Actualiza los datos de un veterinario.
     * ADMIN puede actualizar cualquier veterinario.
     * VETERINARIO solo puede actualizar su propio perfil.
     * @param id ID del veterinario a actualizar.
     * @param request Datos actualizados del veterinario.
     * @return El veterinario actualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_VETERINARIO') and principal.userId == #id)")
    public ResponseEntity<VetResponse> updateVet(@PathVariable Long id, @Valid @RequestBody UpdateVetRequest request) {
        Vet updatedVet = updateVetUseCase.updateVet(
                id,
                request.getName(),
                request.getSpecialty()
        );
        return ResponseEntity.ok(vetDtoMapper.toResponse(updatedVet));
    }

    /**
     * Elimina un veterinario.
     * Solo ADMIN puede eliminar veterinarios.
     * @param id ID del veterinario a eliminar.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede eliminar veterinarios
    public ResponseEntity<Void> deleteVet(@PathVariable Long id) {
        deleteVetUseCase.deleteVet(id);
        return ResponseEntity.noContent().build();
    }
}