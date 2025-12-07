package com.simulacro.simulacro.domain.port.in.appointment;

import com.simulacro.simulacro.domain.model.appointment.Appointment;

public interface AddDiagnosisUseCase {
    Appointment addDiagnosis(Long appointmentId, String description,
                             String treatment, String observations);
}