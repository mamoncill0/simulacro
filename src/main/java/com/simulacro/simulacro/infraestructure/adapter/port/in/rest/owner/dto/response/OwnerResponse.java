package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.owner.dto.response;

public class OwnerResponse {
    private Long id;
    private String name;
    private String document;

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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
