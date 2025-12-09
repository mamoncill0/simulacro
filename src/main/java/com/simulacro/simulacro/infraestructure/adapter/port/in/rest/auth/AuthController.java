package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.auth;

import com.simulacro.simulacro.domain.model.user.User;
import com.simulacro.simulacro.domain.port.in.auth.AuthenticateUserUseCase;
import com.simulacro.simulacro.domain.port.in.auth.RegisterUserUseCase;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.auth.dto.request.AuthenticationRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.auth.dto.request.RegisterRequest;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.auth.dto.response.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la autenticación y registro de usuarios.
 * Expone los endpoints /api/v1/auth/register y /api/v1/auth/login.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor // Genera un constructor con todos los campos final para inyección de dependencias
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    /**
     * Endpoint para registrar un nuevo usuario.
     * @param request DTO con los datos de registro (username, password, role).
     * @return ResponseEntity con el token JWT del usuario registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        User registeredUser = registerUserUseCase.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getRole(),
                request.getOwnerName(), // Añadido
                request.getOwnerDocument() // Añadido
        );
        // Después de registrar, autenticamos al usuario para devolver un token
        String jwtToken = authenticateUserUseCase.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
    }

    /**
     * Endpoint para autenticar un usuario existente (login).
     * @param request DTO con las credenciales (username, password).
     * @return ResponseEntity con el token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        String jwtToken = authenticateUserUseCase.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
    }
}
