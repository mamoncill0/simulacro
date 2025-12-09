package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.exception.VetNotFoundException;
import com.simulacro.simulacro.domain.model.veterinarian.Vet;
import com.simulacro.simulacro.domain.port.in.veterinarian.*;
import com.simulacro.simulacro.domain.port.out.VeterinarianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeterinarianService implements RegisterVetUseCase, FindVetUseCase, UpdateVetUseCase, DeleteVetUseCase {

    private final VeterinarianRepository veterinarianRepository;

    public VeterinarianService(VeterinarianRepository veterinarianRepository) {
        this.veterinarianRepository = veterinarianRepository;
    }

    @Override
    public Vet registerVet(String name, String specialty) {
        // Aquí podrías añadir validaciones de negocio adicionales si fueran necesarias
        Vet newVet = new Vet(name, specialty);
        return veterinarianRepository.save(newVet);
    }

    @Override
    public Optional<Vet> findById(Long id) {
        return veterinarianRepository.findById(id);
    }

    @Override
    public List<Vet> findAll() {
        return veterinarianRepository.findAll();
    }

    @Override
    public Vet updateVet(Long id, String name, String specialty) {
        Vet existingVet = veterinarianRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Veterinario no encontrado con ID: " + id));

        existingVet.setName(name);
        existingVet.setSpecialty(specialty);

        return veterinarianRepository.save(existingVet);
    }

    @Override
    public void deleteVet(Long id) {
        if (!veterinarianRepository.existsById(id)) {
            throw new VetNotFoundException("Veterinario no encontrado con ID: " + id);
        }
        veterinarianRepository.delete(id);
    }
}
