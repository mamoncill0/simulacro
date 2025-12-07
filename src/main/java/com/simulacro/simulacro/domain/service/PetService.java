package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.exception.PetNotFoundException;
import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.port.in.pet.RegisterPetUseCase;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import com.simulacro.simulacro.domain.port.out.PetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetService implements RegisterPetUseCase {


    private PetRepository petRepository;
    private OwnerRepository ownerPet;

    public PetService(PetRepository petRepository, OwnerRepository ownerPet) {
        this.petRepository = petRepository;
        this.ownerPet = ownerPet;
    }

    @Override
    public Pet registerPet(String petName, String species, String breed,
                           Integer age, String ownerName, String ownerDocument){
        //Buscamos al dueño o lo creamos
        OwnerPet owner = ownerPet.findByDocument(ownerDocument).orElseGet(()->{
            OwnerPet newOwner = new OwnerPet(ownerName, ownerDocument);
            return ownerPet.save(newOwner);
        });

        //Crear mascotas
        Pet pet = new Pet(petName, species, breed, age, owner.getId());
        //Guaramos la mascota
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long petId, String name, String breed, Integer age) {
        // Buscar mascota existente
        Pet existingPet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Mascota no encontrada con ID: " + petId));

        // Crear nueva instancia con datos actualizados
        Pet updatedPet = new Pet(
                petId,
                name,
                existingPet.getSpecies(),
                breed,
                age,
                existingPet.getOwner(),
                existingPet.getStatus()
        );

        return petRepository.save(updatedPet);
    }

    @Override
    public void deactivatePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Mascota no encontrada con ID: " + petId));

        pet.getStatus();
        petRepository.save(pet);
    }

    @Override
    public Optional<Pet> findById(long petId) {
        return petRepository.findById(petId);
    }

}
