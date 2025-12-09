package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddDiagnosisRequest {

    @NotBlank(message = "La descripción del diagnóstico es obligatoria")
    @Size(min = 20, message = "El diagnóstico debe tener al menos 20 caracteres")
    private String description;

    private String treatment;
    private String observations;

    // Getters y Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
