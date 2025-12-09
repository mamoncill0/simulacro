package com.simulacro.simulacro.domain.model.owner;

public class OwnerPet {
    private Long id;
    private String name;
    private String document;

    public OwnerPet() {
    }

    //Con este constructor es que creamos un nuevo dueño
    public OwnerPet(Long id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }

    public OwnerPet(String ownerName, String ownerDocument) {
        this.name = ownerName;
        this.document = ownerDocument;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
