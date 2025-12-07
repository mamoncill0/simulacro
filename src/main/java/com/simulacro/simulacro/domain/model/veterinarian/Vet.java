package com.simulacro.simulacro.domain.model.veterinarian;


//En esta clase haremos los datos que tendra el Veterinario, para mirar su disponibilidad
public class Vet {
    private Integer veterinarianId;
    private String nameVeterinarian;

    public Vet() {
    }

    public Vet(Integer veterinarianId, String nameVeterinarian) {
        this.veterinarianId = veterinarianId;
        this.nameVeterinarian = nameVeterinarian;
    }

    public Integer getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(Integer veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public String getNameVeterinarian() {
        return nameVeterinarian;
    }

    public void setNameVeterinarian(String nameVeterinarian) {
        this.nameVeterinarian = nameVeterinarian;
    }
}
