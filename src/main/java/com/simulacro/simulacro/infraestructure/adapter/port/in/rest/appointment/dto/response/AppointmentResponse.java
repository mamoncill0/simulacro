package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.response;

import com.simulacro.simulacro.domain.model.appointment.StatusAppointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponse {
    private Long appointmentId;
    private Long petId;
    private Long vetId;
    private LocalDate date;
    private LocalTime hour;
    private String reason;
    private StatusAppointment status;
    private String diagnosis;

    // Getters y Setters

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public StatusAppointment getStatus() {
        return status;
    }

    public void setStatus(StatusAppointment status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
