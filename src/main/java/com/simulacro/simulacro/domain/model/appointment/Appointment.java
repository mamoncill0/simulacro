package com.simulacro.simulacro.domain.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Long appointmentId;
    private Long petId;
    private Long vetId;
    private LocalDate date;
    private LocalTime hour;
    private String reason;
    private StatusAppointment status;
    private String diagnosis;

    public Appointment() {
    }

    public Appointment(Long appointmentId, Long petId, Long vetId, LocalDate date, LocalTime hour,
                       String reason, StatusAppointment status, String diagnosis) {
        this.appointmentId = appointmentId;
        this.petId = petId;
        this.vetId = vetId;
        this.date = date;
        this.hour = hour;
        this.reason = reason;
        this.status = status;
        this.diagnosis = diagnosis;
    }

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
