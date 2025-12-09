package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.domain.port.in.owner.FindOwnerUseCase;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService implements FindOwnerUseCase {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Optional<OwnerPet> findById(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Optional<OwnerPet> findByDocument(String document) {
        return ownerRepository.findByDocument(document);
    }

    @Override
    public List<OwnerPet> findAll() {
        return ownerRepository.findAll();
    }
}
