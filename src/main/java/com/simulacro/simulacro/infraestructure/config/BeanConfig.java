package com.simulacro.simulacro.infraestructure.config;

import com.simulacro.simulacro.domain.port.in.owner.FindOwnerUseCase;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import com.simulacro.simulacro.domain.service.OwnerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Spring - Conecta todos los componentes
 * Aquí es donde le decimos a Spring cómo crear los servicios
 * Cuando Spring inicia busca @Configuration, luego encuentra @Component en PetRepositoryImpl y crea la instancia.
 */
@Configuration
public class BeanConfig {

    // Los casos de uso de PET, APPOINTMENT y VETERINARIAN ahora son gestionados por @Service
    // en PetService, AppointmentService y VeterinarianService respectivamente.
    // Por lo tanto, se eliminan las definiciones @Bean duplicadas aquí.

    // ========== OWNER USE CASES ==========
    // OwnerService no tiene @Service porque solo tiene un caso de uso FindOwnerUseCase
    // y no hay otros servicios de Owner que implementen esta interfaz.
    @Bean
    public FindOwnerUseCase findOwnerUseCase(OwnerRepository ownerRepository) {
        return new OwnerService(ownerRepository);
    }
}
