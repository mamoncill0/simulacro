package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.auth.dto.request;

import com.simulacro.simulacro.domain.model.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la petición de registro de un nuevo usuario.
 * Contiene los datos necesarios para crear una cuenta de usuario.
 */
@Data // Genera getters, setters, toString, equals, hashCode
@Builder // Permite construir objetos de forma fluida
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Role role;

    // Campos opcionales, solo relevantes si el rol es ROLE_DUEÑO
    private String ownerName;
    private String ownerDocument;
}
