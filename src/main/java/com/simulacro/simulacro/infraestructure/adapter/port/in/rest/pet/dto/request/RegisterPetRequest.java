package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.dto.request;

    // Este objeto es para HTTP
    // Tiene validaciones técnicas: @NotBlank, @Size, etc.


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RegisterPetRequest {
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String petName;

    @NotBlank(message = "La especie es obligatoria")
    private String species;

    private String breed; // La raza puede ser opcional

    @NotNull(message = "La edad es obligatoria")
    @Positive(message = "La edad debe ser un número positivo")
    private Integer age;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    private String ownerName;

    @NotBlank(message = "El documento del dueño es obligatorio")
    private String ownerDocument;

    // Getters y Setters

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
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
}
