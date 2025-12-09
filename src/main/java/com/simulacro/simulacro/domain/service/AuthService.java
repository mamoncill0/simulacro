package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.exception.InvalidCredentialsException;
import com.simulacro.simulacro.domain.exception.UserAlreadyExistsException;
import com.simulacro.simulacro.domain.model.owner.OwnerPet;
import com.simulacro.simulacro.domain.model.user.Role;
import com.simulacro.simulacro.domain.model.user.User;
import com.simulacro.simulacro.domain.port.in.auth.AuthenticateUserUseCase;
import com.simulacro.simulacro.domain.port.in.auth.RegisterUserUseCase;
import com.simulacro.simulacro.domain.port.out.OwnerRepository;
import com.simulacro.simulacro.domain.port.out.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de dominio para la autenticación y registro de usuarios.
 * Implementa los casos de uso {@link RegisterUserUseCase} y {@link AuthenticateUserUseCase}.
 */
@Service
public class AuthService implements RegisterUserUseCase, AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository; // Añadido
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor para inyectar las dependencias necesarias.
     * @param userRepository Repositorio para la gestión de usuarios.
     * @param ownerRepository Repositorio para la gestión de dueños.
     * @param passwordEncoder Codificador de contraseñas.
     * @param jwtService Servicio para la gestión de JWT.
     * @param authenticationManager Gestor de autenticación de Spring Security.
     */
    public AuthService(UserRepository userRepository, OwnerRepository ownerRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param username El nombre de usuario deseado.
     * @param password La contraseña del usuario.
     * @param role El rol asignado al usuario.
     * @param ownerName Nombre del dueño (opcional, solo para ROLE_DUEÑO).
     * @param ownerDocument Documento del dueño (opcional, solo para ROLE_DUEÑO).
     * @return El objeto User registrado.
     * @throws UserAlreadyExistsException si el nombre de usuario ya está en uso.
     * @throws IllegalArgumentException si los datos del dueño son inconsistentes para ROLE_DUEÑO.
     */
    @Override
    public User registerUser(String username, String password, Role role, String ownerName, String ownerDocument) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("El nombre de usuario '" + username + "' ya está en uso.");
        }

        Long associatedOwnerPetId = null;
        if (role == Role.ROLE_DUEÑO) {
            if (ownerName == null || ownerName.trim().isEmpty() || ownerDocument == null || ownerDocument.trim().isEmpty()) {
                throw new IllegalArgumentException("Para el rol DUEÑO, el nombre y documento del dueño son obligatorios.");
            }
            // Buscar o crear OwnerPet
            OwnerPet owner = ownerRepository.findByDocument(ownerDocument).orElseGet(() -> {
                OwnerPet newOwner = new OwnerPet(ownerName, ownerDocument);
                return ownerRepository.save(newOwner);
            });
            associatedOwnerPetId = owner.getId();
        }

        // Encriptar la contraseña antes de crear el objeto de dominio
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, encodedPassword, role, associatedOwnerPetId);
        return userRepository.save(newUser);
    }

    /**
     * Autentica a un usuario con sus credenciales y genera un token JWT.
     * @param username El nombre de usuario.
     * @param password La contraseña.
     * @return El token JWT si la autenticación es exitosa.
     * @throws InvalidCredentialsException si las credenciales son inválidas.
     */
    @Override
    public String authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Credenciales inválidas para el usuario: " + username);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Usuario no encontrado después de autenticación exitosa."));

        // Generar token con los claims del usuario
        return jwtService.generateToken(user); // Ahora JwtService acepta el objeto User directamente
    }
}
