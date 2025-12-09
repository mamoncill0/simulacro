package com.simulacro.simulacro.domain.port.in.appointment;

import com.simulacro.simulacro.domain.model.appointment.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

public interface CreateAppointmentUseCase {
    Appointment createAppointment(Long petId, Long veterinarianId,
                                  LocalDate date, LocalTime time, String reason);
}