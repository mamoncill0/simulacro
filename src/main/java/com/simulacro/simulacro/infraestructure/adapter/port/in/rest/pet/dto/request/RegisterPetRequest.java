package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet.dto.request;

    // Este objeto es para HTTP
    // Tiene validaciones técnicas: @NotBlank, @Size, etc.



public class RegisterPetRequest {
    private String petName;
    private String species;
    private String breed;
    private Integer age;
    private String ownerName;
    private String ownerDocument;
}