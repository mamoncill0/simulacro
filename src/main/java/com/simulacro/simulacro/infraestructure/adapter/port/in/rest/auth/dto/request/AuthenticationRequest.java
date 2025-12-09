package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la petición de autenticación (login) de un usuario.
 * Contiene las credenciales necesarias para iniciar sesión.
 */
@Data // Genera getters, setters, toString, equals, hashCode
@Builder // Permite construir objetos de forma fluida
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class AuthenticationRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
