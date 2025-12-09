package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.owner;

import com.simulacro.simulacro.domain.port.in.owner.FindOwnerUseCase;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.owner.dto.response.OwnerResponse;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.owner.mapper.OwnerDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importar PreAuthorize
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    private final FindOwnerUseCase findOwnerUseCase;
    private final OwnerDtoMapper ownerDtoMapper;

    public OwnerController(FindOwnerUseCase findOwnerUseCase, OwnerDtoMapper ownerDtoMapper) {
        this.findOwnerUseCase = findOwnerUseCase;
        this.ownerDtoMapper = ownerDtoMapper;
    }

    /**
     * Obtiene un dueño por su ID.
     * ADMIN puede ver cualquier dueño.
     * DUEÑO solo puede ver su propio perfil.
     * @param id ID del dueño.
     * @return El dueño encontrado.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_DUEÑO') and @ownerSecurity.isOwner(#id))")
    public ResponseEntity<OwnerResponse> getOwnerById(@PathVariable Long id) {
        return findOwnerUseCase.findById(id)
                .map(ownerDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene un dueño por su documento.
     * ADMIN puede ver cualquier dueño.
     * DUEÑO solo puede ver su propio perfil.
     * @param document Documento del dueño.
     * @return El dueño encontrado.
     */
    @GetMapping("/document/{document}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_DUEÑO') and @ownerSecurity.isOwnerByDocument(#document))")
    public ResponseEntity<OwnerResponse> getOwnerByDocument(@PathVariable String document) {
        return findOwnerUseCase.findByDocument(document)
                .map(ownerDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todos los dueños.
     * Solo ADMIN puede ver todos los dueños.
     * @return Lista de todos los dueños.
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede ver todos los dueños
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        List<OwnerResponse> owners = findOwnerUseCase.findAll().stream()
                .map(ownerDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(owners);
    }
}
