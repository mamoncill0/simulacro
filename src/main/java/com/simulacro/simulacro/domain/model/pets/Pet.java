package com.simulacro.simulacro.domain.model.pets;

public class Pet {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String owner;
    private String ownerDocument;
    private Status status;


    public Pet() {
    }

    public Pet(Long id, String name, String species, String breed, Integer age, String owner, String ownerDocument, Status status) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.owner = owner;
        this.ownerDocument = ownerDocument;
        this.status = status;

    }

    public Pet(String petName, String species, String breed, Integer age, Long id) {
        this.name = petName;
        this.species = species;
        this.breed = breed;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
