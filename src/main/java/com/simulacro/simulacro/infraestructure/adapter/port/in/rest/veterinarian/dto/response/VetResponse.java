package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.response;


import com.simulacro.simulacro.domain.model.veterinarian.Vet;

//Aca haremos la respuesta que se mandara en el Swagger o Postman al momento de que tenga una ejecucion correctamente
public class VetResponse {
    private String message;
    private boolean avalability;
    private Vet data;

    public VetResponse() {
    }

    public VetResponse(String message, boolean avalability, Vet data) {
        this.message = message;
        this.avalability = avalability;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAvalability() {
        return avalability;
    }

    public void setAvalability(boolean avalability) {
        this.avalability = avalability;
    }

    public Vet getData() {
        return data;
    }

    public void setData(Vet data) {
        this.data = data;
    }
}
