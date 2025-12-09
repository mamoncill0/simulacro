package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.exception.PetNotFoundException;
import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.domain.model.pets.Pet;
import com.simulacro.simulacro.domain.port.in.pet.DeletePetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.FindPetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.RegisterPetUseCase;
import com.simulacro.simulacro.domain.port.in.pet.UpdatePetUseCase;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import com.simulacro.simulacro.domain.port.out.PetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Cuando sale roja esta linea de PetService implements ...
//Es poque en algun Override estamos sobreescribiendo un metodo o atributos de una interfaz
//A la cual no le estamos agregando todos los valores que declaramos alla.
@Service
public class PetService implements RegisterPetUseCase, UpdatePetUseCase,
                                        DeletePetUseCase, FindPetUseCase {


    private PetRepository petRepository;
    private OwnerRepository ownerPet;

    public PetService(PetRepository petRepository, OwnerRepository ownerPet) {
        this.petRepository = petRepository;
        this.ownerPet = ownerPet;
    }

    @Override
    public Pet registerPet(String petName, String species, String breed,
                           Integer age, String ownerName, String ownerDocument){

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
                petName,
                species,
                breed,
                age,
                owner.getId(),
                owner.getName(),
                owner.getDocument());

        //Guardamos la mascota
        return petRepository.save(pet);
    }

    // Forma correcta de implementar updatePet
    @Override
    public Pet updatePet(Long petId, String name, String breed, Integer age) {
        // 1. Buscar mascota existente
        Pet petToUpdate = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Mascota no encontrada con ID: " + petId));

        // 2. Actualizar sus propiedades usando setters (que contienen las validaciones)
        petToUpdate.setName(name);
        petToUpdate.setBreed(breed);
        petToUpdate.setAge(age);

        // 3. Guardar la entidad modificada
        return petRepository.save(petToUpdate);
    }


    @Override
    public void deactivatePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Mascota no encontrada con ID: " + petId));

        //Desactivamos la mascota (Osea el estado queda en Inactive)
        //El metodo deactivate lo creamos en Pet, le hicimos la logica de Status
        pet.deactivate();

        //Guardamos
        petRepository.save(pet);
    }

    @Override
    public Optional<Pet> findById(Long petId) {
        return petRepository.findById(petId);
    }

}
