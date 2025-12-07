package com.simulacro.simulacro.infraestructure.config;

import com.simulacro.simulacro.domain.port.in.appointment.*;
import com.simulacro.simulacro.domain.port.in.pet.*;
import com.simulacro.simulacro.domain.port.out.AppointmentRepository;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import com.simulacro.simulacro.domain.port.out.PetRepository;
import com.simulacro.simulacro.domain.port.out.VeterinarianRepository;
import com.simulacro.simulacro.domain.service.AppointmentService;
import com.simulacro.simulacro.domain.service.PetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Spring - Conecta todos los componentes
 * Aquí es donde le decimos a Spring cómo crear los servicios
 * Cuando Spring inicia busca @Configuration, luego encuentra @Component en PetRepositoryImpl y crea la instancia.
 */
@Configuration
public class BeanConfig {

    // ========== PET USE CASES ==========

    @Bean
    public RegisterPetUseCase registerPetUseCase(
            PetRepository petRepository, // Spring sabe que debe inyectar PetRepositoryImpl
            OwnerRepository ownerRepository) {

        //Le DECIMOS a Spring cómo crear el servicio
        return new PetService(petRepository, ownerRepository);
    }

    @Bean
    public UpdatePetUseCase updatePetUseCase(PetRepository petRepository,
                                             OwnerRepository ownerRepository) {
        return new PetService(petRepository, ownerRepository);
    }

    @Bean
    public DeletePetUseCase deletePetUseCase(PetRepository petRepository,
                                             OwnerRepository ownerRepository) {
        return new PetService(petRepository, ownerRepository);
    }

    @Bean
    public FindPetUseCase findPetUseCase(PetRepository petRepository,
                                         OwnerRepository ownerRepository) {
        return new PetService(petRepository, ownerRepository);
    }

    // ========== APPOINTMENT USE CASES ==========

    @Bean
    public CreateAppointmentUseCase createAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            PetRepository petRepository,
            VeterinarianRepository veterinarianRepository) {
        return new AppointmentService(appointmentRepository, petRepository, veterinarianRepository);
    }

    @Bean
    public ConfirmAppointmentUseCase confirmAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            PetRepository petRepository,
            VeterinarianRepository veterinarianRepository) {
        return new AppointmentService(appointmentRepository, petRepository, veterinarianRepository);
    }

    @Bean
    public CancelAppointmentUseCase cancelAppointmentUseCase(
            AppointmentRepository appointmentRepository,
            PetRepository petRepository,
            VeterinarianRepository veterinarianRepository) {
        return new AppointmentService(appointmentRepository, petRepository, veterinarianRepository);
    }

    @Bean
    public AddDiagnosisUseCase addDiagnosisUseCase(
            AppointmentRepository appointmentRepository,
            PetRepository petRepository,
            VeterinarianRepository veterinarianRepository) {
        return new AppointmentService(appointmentRepository, petRepository, veterinarianRepository);
    }
}