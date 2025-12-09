package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterVetRequest {

    @NotBlank(message = "El nombre del veterinario es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La especialidad del veterinario es obligatoria")
    @Size(min = 3, max = 100, message = "La especialidad debe tener entre 3 y 100 caracteres")
    private String specialty;

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
