package com.simulacro.simulacro.infraestructure.config;

import com.simulacro.simulacro.infraestructure.config.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración principal para Spring Security.
 * Define la cadena de filtros de seguridad, las reglas de autorización,
 * la gestión de sesiones y la integración del filtro JWT.
 */
@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring
@RequiredArgsConstructor // Genera un constructor con todos los campos final
@EnableMethodSecurity // Habilita la seguridad a nivel de método (ej. @PreAuthorize)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * Define las reglas de autorización para diferentes endpoints,
     * deshabilita CSRF, configura la gestión de sesiones como STATELESS
     * e integra el filtro JWT.
     * @param http Objeto HttpSecurity para configurar la seguridad.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF ya que usaremos JWT (stateless)
                .authorizeHttpRequests(auth -> auth
                        // Permite acceso sin autenticación a los endpoints de autenticación y Swagger
                        .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Requiere autenticación para cualquier otra petición
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        // Configura la gestión de sesiones como STATELESS (sin estado), esencial para JWT
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider) // Establece el proveedor de autenticación
                // Añade el filtro JWT antes del filtro de autenticación de usuario/contraseña estándar de Spring
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
