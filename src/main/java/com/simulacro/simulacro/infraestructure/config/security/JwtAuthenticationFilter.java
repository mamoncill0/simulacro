package com.simulacro.simulacro.infraestructure.config.security;

import com.simulacro.simulacro.domain.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT que se ejecuta una vez por cada petición HTTP.
 * Intercepta las peticiones para validar la presencia y validez de un token JWT.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Realiza el filtrado de la petición.
     * Extrae el token JWT del encabezado de autorización, lo valida y autentica al usuario.
     * @param request La petición HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de E/S.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Verificar si el encabezado de autorización está presente y tiene el formato correcto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token JWT
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt); // Extraer el nombre de usuario del token

        // 3. Si el nombre de usuario no es nulo y no hay una autenticación existente en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargar los detalles del usuario
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. Validar el token
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Si el token es válido, crear un objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Las credenciales son nulas después de la autenticación con token
                        userDetails.getAuthorities() // Obtener las autoridades del usuario
                );
                // Establecer detalles de la autenticación (dirección IP, sesión, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
