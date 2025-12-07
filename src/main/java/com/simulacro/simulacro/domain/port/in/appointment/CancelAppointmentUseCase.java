package com.simulacro.simulacro.domain.port.in.appointment;

import com.simulacro.simulacro.domain.model.appointment.Appointment;

public interface CancelAppointmentUseCase {
    Appointment cancelAppointment(Long appointmentId);
}
