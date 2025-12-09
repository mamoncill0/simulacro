package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity;

import com.simulacro.simulacro.domain.model.appointment.StatusAppointment;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "veterinarian_id", nullable = false)
    private Long veterinarianId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime hour;

    @Column(nullable = false, length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusAppointment status;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    // Constructor vacío
    public AppointmentEntity() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }

    public Long getVeterinarianId() { return veterinarianId; }
    public void setVeterinarianId(Long veterinarianId) { this.veterinarianId = veterinarianId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHour() { return hour; }
    public void setHour(LocalTime hour) { this.hour = hour; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public StatusAppointment getStatus() { return status; }
    public void setStatus(StatusAppointment status) { this.status = status; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
}