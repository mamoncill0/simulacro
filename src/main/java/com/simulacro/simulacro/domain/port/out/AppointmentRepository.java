package com.simulacro.simulacro.domain.port.out;

import com.simulacro.simulacro.domain.model.appointment.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long appointmentId);
    List<Appointment> findAll(); // Añadido
    List<Appointment> findByPetId(Long petId);
    List<Appointment> findByVeterinarianId(Long veterinarianId);
    List<Appointment> findByDate(LocalDate date);
    boolean existsByVeterinarianIdAndDateAndTime(Long veterinarianId,
                                                 LocalDate date, LocalTime time);
    void delete(Long appointmentId);
}
