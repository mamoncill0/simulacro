package com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "owners")
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String document;

    // Constructor vacío (obligatorio para JPA)
    protected OwnerEntity() {}

    public OwnerEntity(String name, String document) {
        this.name = name;
        this.document = document;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }
}