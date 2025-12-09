package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de una autenticación exitosa.
 * Contiene el token JWT que el cliente debe usar para futuras peticiones autenticadas.
 */
@Data // Genera getters, setters, toString, equals, hashCode
@Builder // Permite construir objetos de forma fluida
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class AuthenticationResponse {
    private String token;
}
