package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.response;

public class VetResponse {
    private Long id; // Cambiado de Integer veterinarianId a Long id
    private String name; // Cambiado de nameVeterinarian a name
    private String specialty; // Añadido

    // Constructor vacío
    public VetResponse() {
    }

    // Constructor completo
    public VetResponse(Long id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
