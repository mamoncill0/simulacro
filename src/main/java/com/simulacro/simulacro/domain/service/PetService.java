package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.exception.PetNotFoundException;
import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.model.pets.Status;
import com.simulacro.simulacro.domain.port.in.pet.DeletePetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.FindPetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.RegisterPetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.UpdatePetUseCase;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import com.simulacro.simulacro.domain.port.out.PetRepository;

import java.util.Optional;

//Cuando sale roja esta linea de PetService implements ...
//Es poque en algun Override estamos sobreescribiendo un metodo o atributos de una interfaz
//A la cual no le estamos agregando todos los valores que declaramos alla.
public class PetService implements RegisterPetUseCase, UpdatePetUseCase, DeletePetUseCase, FindPetUseCase {


    private PetRepository petRepository;
    private OwnerRepository ownerPet;

    public PetService(PetRepository petRepository, OwnerRepository ownerPet) {
        this.petRepository = petRepository;
        this.ownerPet = ownerPet;
    }

    @Override
    public Pet registerPet(String petName, String species, String breed,
                           Integer age, String ownerName, String ownerDocument, Status status){

        // 1. Validar documento del dueño
        if (ownerDocument == null || ownerDocument.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento del dueño es obligatorio");
        }

        //Buscamos al dueño o lo creamos
        OwnerPet owner = ownerPet.findByDocument(ownerDocument).orElseGet(()->{
            OwnerPet newOwner = new OwnerPet(ownerName, ownerDocument);
            return ownerPet.save(newOwner);
        });

        //Crear mascotas (las validaciones están en el constructor de Pet)
        Pet pet = new Pet(
                owner.getId(),
                petName, species, breed, age,
                owner.getName(), owner.getDocument(),
                status);

        //Guardamos la mascota
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long petId, String name, String breed, Integer age) {

        // Buscar mascota existente
        Pet existingPet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Mascota no encontrada con ID: " + petId));

        // Crear nueva instancia con datos actualizados (Todos estos datos son los que estan en la clase Pet)
        Pet updatedPet = new Pet(
                petId,
                name,
                existingPet.getSpecies(),
                breed,
                age,
                existingPet.getOwner(),
                existingPet.getOwnerDocument(),
                existingPet.getStatus()
        );

        return petRepository.save(updatedPet);
    }

    @Override
    public void deactivatePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Mascota no encontrada con ID: " + petId));

        pet.getStatus().equals(Status.ACTIVE);

        //Guardamos
        petRepository.save(pet);
    }

    @Override
    public Optional<Pet> findById(Long petId) {
        return petRepository.findById(petId);
    }

}
