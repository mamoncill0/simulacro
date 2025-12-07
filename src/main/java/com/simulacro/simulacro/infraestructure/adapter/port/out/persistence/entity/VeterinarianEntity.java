package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "veterinarians")
public class VeterinarianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String specialty;

    // Constructor vacío
    protected VeterinarianEntity() {}

    public VeterinarianEntity(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}