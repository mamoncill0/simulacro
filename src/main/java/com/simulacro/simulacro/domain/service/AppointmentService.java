package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.exception.InvalidAppointmentException;
import com.simulacro.simulacro.domain.exception.PetNotFoundException;
import com.simulacro.simulacro.domain.exception.VetNotAvailableException;
import com.simulacro.simulacro.domain.model.appointment.Appointment;
import com.simulacro.simulacro.domain.model.appointment.StatusAppointment;
import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.model.pets.Status;
import com.simulacro.simulacro.domain.port.in.appointment.*;
import com.simulacro.simulacro.domain.port.out.AppointmentRepository;
import com.simulacro.simulacro.domain.port.out.PetRepository;
import com.simulacro.simulacro.domain.port.out.VeterinarianRepository;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Servicio del dominio para gestión de citas médicas
 */
public class AppointmentService implements CreateAppointmentUseCase,
        ConfirmAppointmentUseCase,
        CancelAppointmentUseCase,
        AddDiagnosisUseCase {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VeterinarianRepository veterinarianRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PetRepository petRepository,
                              VeterinarianRepository veterinarianRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.veterinarianRepository = veterinarianRepository;
    }

    @Override
    public Appointment createAppointment(Long petId, Long veterinarianId,
                                         LocalDate date, LocalTime time, String reason, StatusAppointment status, String diagnosis) {

        // 1. Validar que la mascota existe
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Mascota no encontrada con ID: " + petId));

        // 2. REGLA DE NEGOCIO: Solo mascotas ACTIVAS pueden agendar citas
        if (!pet.getStatus().equals(Status.ACTIVE)) {
            throw new InvalidAppointmentException(
                    "La mascota '" + pet.getName() + "' está INACTIVA y no puede solicitar citas. " +
                            "Por favor, active la mascota primero."
            );
        }

        // 3. Validar que el veterinario existe
        if (!veterinarianRepository.existsById(veterinarianId)) {
            throw new InvalidAppointmentException("Veterinario no encontrado con ID: " + veterinarianId);
        }

        // 4. REGLA DE NEGOCIO: Verificar disponibilidad del veterinario
        boolean isOccupied = appointmentRepository.existsByVeterinarianIdAndDateAndTime(
                veterinarianId, date, time
        );

        if (isOccupied) {
            throw new VetNotAvailableException(
                    "El veterinario no está disponible el " + date + " a las " + time + ". " +
                            "Ya tiene una cita agendada en ese horario."
            );
        }

        // 5. Validar horario de atención (8:00 AM - 6:00 PM)
        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(18, 0))) {
            throw new InvalidAppointmentException(
                    "El horario de atención es de 8:00 AM a 6:00 PM. Hora solicitada: " + time
            );
        }

        // 6. Validar que no sea fecha pasada
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidAppointmentException("No se pueden crear citas en fechas pasadas");
        }

        // 7. Crear y guardar la cita
        Appointment appointment = new Appointment(
                null,
                petId,
                veterinarianId,
                date,
                time,
                reason,
                StatusAppointment.Pending,
                null
        );

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment confirmAppointment(Long appointmentId) {
        // 1. Buscar cita
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new InvalidAppointmentException(
                        "Cita no encontrada con ID: " + appointmentId
                ));

        // 2. Validar que esté en estado PENDING
        if (appointment.getStatus() != StatusAppointment.Pending) {
            throw new InvalidAppointmentException(
                    "Solo se pueden confirmar citas en estado PENDIENTE. Estado actual: " +
                            appointment.getStatus()
            );
        }

        // 3. Cambiar estado
        appointment.setStatus(StatusAppointment.Confirmed);

        // 4. Guardar
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment cancelAppointment(Long appointmentId) {
        // 1. Buscar cita
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new InvalidAppointmentException(
                        "Cita no encontrada con ID: " + appointmentId
                ));

        // 2. Validar que no esté ya cancelada
        if (appointment.getStatus() == StatusAppointment.Cancelled) {
            throw new InvalidAppointmentException("La cita ya está cancelada");
        }

        // 3. Cambiar estado
        appointment.setStatus(StatusAppointment.Cancelled);

        // 4. Guardar
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment addDiagnosis(Long appointmentId, String description,
                                    String treatment, String observations) {

        // 1. Buscar cita
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new InvalidAppointmentException(
                        "Cita no encontrada con ID: " + appointmentId
                ));

        // 2. Validar que no esté cancelada
        if (appointment.getStatus() == StatusAppointment.Cancelled) {
            throw new InvalidAppointmentException(
                    "No se puede agregar diagnóstico a una cita cancelada"
            );
        }

        // 3. Validar descripción
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del diagnóstico es obligatoria");
        }

        if (description.length() < 20) {
            throw new IllegalArgumentException(
                    "El diagnóstico debe tener al menos 20 caracteres"
            );
        }

        // 4. Construir diagnóstico completo
        String fullDiagnosis = "DIAGNÓSTICO: " + description.trim();
        if (treatment != null && !treatment.trim().isEmpty()) {
            fullDiagnosis += "\nTRATAMIENTO: " + treatment.trim();
        }
        if (observations != null && !observations.trim().isEmpty()) {
            fullDiagnosis += "\nOBSERVACIONES: " + observations.trim();
        }

        // 5. Agregar diagnóstico
        appointment.setDiagnosis(fullDiagnosis);

        // 6. Guardar
        return appointmentRepository.save(appointment);
    }
}