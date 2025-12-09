package com.simulacro.simulacro.infraestructure.config;

import com.simulacro.simulacro.domain.exception.UserNotFoundException;
import com.simulacro.simulacro.domain.port.out.UserRepository;
import com.simulacro.simulacro.infraestructure.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections; // Importar Collections

/**
 * Clase de configuración para la aplicación, exponiendo beans esenciales para Spring Security.
 */
@Configuration
@RequiredArgsConstructor // Genera un constructor con todos los campos final, para inyección de dependencias
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Define el UserDetailsService, que es responsable de cargar los detalles del usuario
     * durante el proceso de autenticación.
     * @return Una implementación de UserDetailsService que busca usuarios en el UserRepository.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> new CustomUserDetails( // Usar CustomUserDetails
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole()), // Corregido: Pasar el rol como una colección
                        user.getId(), // Pasar el userId
                        user.getOwnerPetId()
                ))
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + username));
    }

    /**
     * Define el AuthenticationProvider, que es el componente que realiza la autenticación.
     * Utiliza DaoAuthenticationProvider para autenticar usuarios contra un UserDetailsService
     * y un PasswordEncoder.
     * @return Un AuthenticationProvider configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Define el AuthenticationManager, el gestor principal del proceso de autenticación.
     * @param config La configuración de autenticación.
     * @return El AuthenticationManager.
     * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define el PasswordEncoder, utilizado para encriptar y verificar contraseñas.
     * Se recomienda BCryptPasswordEncoder por su seguridad.
     * @return Un BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
