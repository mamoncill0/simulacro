package com.simulacro.simulacro.domain.model.veterinarian;


//En esta clase haremos los datos que tendra el Veterinario, para mirar su disponibilidad
public class Vet {
    private Long id; // Cambiado de Integer veterinarianId a Long id
    private String name; // Cambiado de nameVeterinarian a name
    private String specialty; // Añadido

    public Vet() {
    }

    // Constructor completo para reconstruir desde BD
    public Vet(Long id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    // Constructor para crear nuevo veterinario (sin ID)
    public Vet(String name, String specialty) {
        this(null, name, specialty);
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
