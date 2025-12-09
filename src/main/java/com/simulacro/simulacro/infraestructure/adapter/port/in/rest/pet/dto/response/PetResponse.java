package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.dto.response;

import com.simulacro.simulacro.domain.model.pets.Status;

public class PetResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private Long ownerId;
    private String ownerName;
    private String ownerDocument;
    private Status status;

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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerDocument() {
        return ownerDocument;
    }

    public void setOwnerDocument(String ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
