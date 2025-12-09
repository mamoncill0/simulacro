package com.simulacro.simulacro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Esto le indica explícitamente a Spring que escanee el paquete com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository
en busca de interfaces de repositorio. Al hacerlo, encontrará PetJpaRepository (y las otras interfaces de JpaRepository),
creará los beans necesarios, y podrá inyectarlos correctamente en tus clases ...RepositoryImpl.
 */
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.simulacro.simulacro.infraestructure.adapter.port.out.persistence.repository")
public class SimulacroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulacroApplication.class, args);
	}

}
