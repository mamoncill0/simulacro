package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import com.simulacro.simulacro.domain.port.in.appointment.*;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.request.AddDiagnosisRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.request.CreateAppointmentRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.response.AppointmentResponse;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.mapper.AppointmentDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importar PreAuthorize
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final FindAppointmentUseCase findAppointmentUseCase;
    private final ConfirmAppointmentUseCase confirmAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final AddDiagnosisUseCase addDiagnosisUseCase;
    private final AppointmentDtoMapper appointmentDtoMapper;

    public AppointmentController(CreateAppointmentUseCase createAppointmentUseCase,
                                 FindAppointmentUseCase findAppointmentUseCase,
                                 ConfirmAppointmentUseCase confirmAppointmentUseCase,
                                 CancelAppointmentUseCase cancelAppointmentUseCase,
                                 AddDiagnosisUseCase addDiagnosisUseCase,
                                 AppointmentDtoMapper appointmentDtoMapper) {
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.findAppointmentUseCase = findAppointmentUseCase;
        this.confirmAppointmentUseCase = confirmAppointmentUseCase;
        this.cancelAppointmentUseCase = cancelAppointmentUseCase;
        this.addDiagnosisUseCase = addDiagnosisUseCase;
        this.appointmentDtoMapper = appointmentDtoMapper;
    }

    /**
     * Crea una nueva cita.
     * Solo ADMIN puede crear citas.
     * @param request Datos de la cita a crear.
     * @return La cita creada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede crear citas
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment newAppointment = createAppointmentUseCase.createAppointment(
                request.getPetId(),
                request.getVeterinarianId(),
                request.getDate(),
                request.getHour(),
                request.getReason()
        );
        return ResponseEntity.status(201).body(appointmentDtoMapper.toResponse(newAppointment));
    }

    /**
     * Obtiene una cita por su ID.
     * ADMIN puede ver cualquier cita.
     * VETERINARIO solo puede ver citas asignadas a él.
     * DUEÑO solo puede ver citas de sus mascotas.
     * @param id ID de la cita.
     * @return La cita encontrada.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_VETERINARIO') and @appointmentSecurity.isAssignedToVet(#id)) or (hasRole('ROLE_DUEÑO') and @appointmentSecurity.isOwnerOfAppointment(#id))")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        return findAppointmentUseCase.findById(id)
                .map(appointmentDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene todas las citas.
     * Solo ADMIN puede ver todas las citas.
     * @return Lista de todas las citas.
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo ADMIN puede ver todas las citas
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> appointments = findAppointmentUseCase.findAll().stream()
                .map(appointmentDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    /**
     * Obtiene todas las citas de una mascota específica.
     * ADMIN puede ver todas las citas de cualquier mascota.
     * DUEÑO solo puede ver citas de sus propias mascotas.
     * @param petId ID de la mascota.
     * @return Lista de citas de la mascota.
     */
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_DUEÑO') and @petSecurity.isOwner(#petId))")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByPetId(@PathVariable Long petId) {
        List<AppointmentResponse> appointments = findAppointmentUseCase.findAllByPetId(petId).stream()
                .map(appointmentDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    /**
     * Obtiene todas las citas de un veterinario específico.
     * ADMIN puede ver todas las citas de cualquier veterinario.
     * VETERINARIO solo puede ver sus propias citas asignadas.
     * @param vetId ID del veterinario.
     * @return Lista de citas del veterinario.
     */
    @GetMapping("/vet/{vetId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_VETERINARIO') and principal.userId == #vetId)") // principal.userId es el ID del usuario autenticado
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByVetId(@PathVariable Long vetId) {
        List<AppointmentResponse> appointments = findAppointmentUseCase.findAllByVetId(vetId).stream()
                .map(appointmentDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    /**
     * Confirma una cita.
     * ADMIN puede confirmar cualquier cita.
     * VETERINARIO solo puede confirmar citas asignadas a él.
     * @param id ID de la cita a confirmar.
     * @return La cita confirmada.
     */
    @PutMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_VETERINARIO') and @appointmentSecurity.isAssignedToVet(#id))")
    public ResponseEntity<AppointmentResponse> confirmAppointment(@PathVariable Long id) {
        Appointment confirmedAppointment = confirmAppointmentUseCase.confirmAppointment(id);
        return ResponseEntity.ok(appointmentDtoMapper.toResponse(confirmedAppointment));
    }

    /**
     * Cancela una cita.
     * ADMIN puede cancelar cualquier cita.
     * VETERINARIO solo puede cancelar citas asignadas a él.
     * DUEÑO solo puede cancelar sus propias citas.
     * @param id ID de la cita a cancelar.
     * @return La cita cancelada.
     */
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_VETERINARIO') and @appointmentSecurity.isAssignedToVet(#id)) or (hasRole('ROLE_DUEÑO') and @appointmentSecurity.isOwnerOfAppointment(#id))")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable Long id) {
        Appointment cancelledAppointment = cancelAppointmentUseCase.cancelAppointment(id);
        return ResponseEntity.ok(appointmentDtoMapper.toResponse(cancelledAppointment));
    }

    /**
     * Añade un diagnóstico a una cita.
     * ADMIN puede añadir diagnóstico a cualquier cita.
     * VETERINARIO solo puede añadir diagnóstico a citas asignadas a él.
     * @param id ID de la cita.
     * @param request Datos del diagnóstico.
     * @return La cita con el diagnóstico añadido.
     */
    @PutMapping("/{id}/diagnosis")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_VETERINARIO') and @appointmentSecurity.isAssignedToVet(#id))")
    public ResponseEntity<AppointmentResponse> addDiagnosis(@PathVariable Long id, @Valid @RequestBody AddDiagnosisRequest request) {
        Appointment appointmentWithDiagnosis = addDiagnosisUseCase.addDiagnosis(
                id,
                request.getDescription(),
                request.getTreatment(),
                request.getObservations()
        );
        return ResponseEntity.ok(appointmentDtoMapper.toResponse(appointmentWithDiagnosis));
    }
}
