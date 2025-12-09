package com.simulacro.simulacro.domain.model.pets;

/**
 * Modelo de dominio: Pet (Mascota)
 * Contiene lógica de negocio y validaciones
 */
public class Pet {
    private Long id;
    private String name;
    private String species;  // PERRO, GATO, AVE, OTRO
    private String breed;
    private Integer age;
    private Long ownerId;
    private String ownerName;
    private String ownerDocument;
    private Status status;

    // Constructor vacío
    public Pet() {
    }

    // Constructor completo (para reconstruir desde BD)
    public Pet(Long id, String name, String species, String breed, Integer age,
               String ownerName, String ownerDocument, Status status) {
        validateName(name);
        validateSpecies(species);
        validateAge(age);

        this.id = id;
        this.name = name.trim();
        this.species = species.toUpperCase();
        this.breed = breed != null ? breed.trim() : "Desconocida";
        this.age = age;
        this.ownerId = null; // Se setea después
        this.ownerName = ownerName;
        this.ownerDocument = ownerDocument;
        this.status = status != null ? status : Status.Active;
    }

    // Constructor para crear nueva mascota (sin ID)
    public Pet(String name, String species, String breed, Integer age,
               Long ownerId, String ownerName, String ownerDocument) {
        this(null, name, species, breed, age, ownerName, ownerDocument, Status.Active);
        this.ownerId = ownerId;
    }

    // ========== REGLAS DE NEGOCIO ==========

    public boolean canRequestAppointment() {
        return this.status == Status.Active;
    }

    public void deactivate() {
        if (this.status == Status.Inactive) {
            throw new IllegalStateException("La mascota ahora está inactiva");
        }
        this.status = Status.Inactive;
    }

    public void activate() {
        if (this.status == Status.Active) {
            throw new IllegalStateException("La mascota ahora está activa");
        }
        this.status = Status.Active;
    }

    // ========== VALIDACIONES ==========

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 2 y 50 caracteres");
        }
    }

    private void validateSpecies(String species) {
        if (species == null || species.trim().isEmpty()) {
            throw new IllegalArgumentException("La especie es obligatoria");
        }
        String upperSpecies = species.toUpperCase();
        if (!upperSpecies.equals("PERRO") && !upperSpecies.equals("GATO") &&
                !upperSpecies.equals("AVE") && !upperSpecies.equals("OTRO")) {
            throw new IllegalArgumentException("Especie no válida. Debe ser: PERRO, GATO, AVE u OTRO");
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor a 0");
        }
        if (age > 50) {
            throw new IllegalArgumentException("La edad no puede ser mayor a 50 años");
        }
    }

    // ========== GETTERS Y SETTERS ==========

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
        validateName(name);
        this.name = name.trim();
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        validateSpecies(species);
        this.species = species.toUpperCase();
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed != null ? breed.trim() : "Desconocida";
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        validateAge(age);
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

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", status=" + status +
                '}';
    }
}