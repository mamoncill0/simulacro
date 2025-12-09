package com.simulacro.simulacro.domain.port.in.appointment;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import java.util.List;
import java.util.Optional;

public interface FindAppointmentUseCase {
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    List<Appointment> findAllByPetId(Long petId);
    List<Appointment> findAllByVetId(Long vetId);
}
