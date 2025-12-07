package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity;

import com.simulacro.simulacro.domain.model.pets.Status;
import jakarta.persistence.*;

    // Este objeto es para mapear la tabla de BD
    // Tiene anotaciones de JPA: @Entity, @Column, etc.

@Entity
@Table(name = "pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 10)
    private String species;  // PERRO, GATO, AVE, OTRO

    @Column(length = 50)
    private String breed;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_document")
    private String ownerDocument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    // Constructor vacío
    public PetEntity() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getOwnerDocument() { return ownerDocument; }
    public void setOwnerDocument(String ownerDocument) { this.ownerDocument = ownerDocument; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
