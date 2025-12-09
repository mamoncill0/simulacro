package com.simulacro.simulacro.domain.service;

import com.simulacro.simulacro.domain.model.user.Role;
import com.simulacro.simulacro.domain.model.user.User;
import com.simulacro.simulacro.infraestructure.config.security.CustomUserDetails; // Importar CustomUserDetails
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections; // Importar Collections
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para la gestión de JSON Web Tokens (JWT).
 * Se encarga de la generación, extracción de información y validación de tokens.
 */
@Service
public class JwtService {

    // Se inyecta la clave secreta desde las propiedades de la aplicación (application.properties)
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    // Se inyecta el tiempo de expiración del token desde las propiedades de la aplicación
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     * @param token El token JWT.
     * @return El nombre de usuario.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un claim específico del token JWT.
     * @param token El token JWT.
     * @param claimsResolver Función para resolver el claim deseado.
     * @param <T> Tipo del claim.
     * @return El valor del claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT para un UserDetails de Spring Security.
     * @param userDetails Detalles del usuario.
     * @return El token JWT generado.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT para un objeto User del dominio, incluyendo claims personalizados.
     * @param user El objeto User del dominio.
     * @return El token JWT generado.
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name()); // Añadir el rol como claim
        if (user.getRole() == Role.ROLE_DUEÑO && user.getOwnerPetId() != null) {
            claims.put("ownerPetId", user.getOwnerPetId()); // Añadir ownerPetId si es DUEÑO
        }
        // Usar CustomUserDetails para la generación del token, asegurando que ownerPetId esté disponible
        // Corregido: user.getRole() se envuelve en Collections.singleton()
        UserDetails userDetails = new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(user.getRole()), // Corregido: Pasar el rol como una colección
                user.getId(), // Asegúrate de que user.getId() devuelve el ID del usuario
                user.getOwnerPetId()
        );
        return generateToken(claims, userDetails);
    }

    /**
     * Genera un token JWT con claims adicionales.
     * @param extraClaims Claims adicionales a incluir en el token.
     * @param userDetails Detalles del usuario.
     * @return El token JWT generado.
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida si un token JWT es válido para un usuario.
     * @param token El token JWT.
     * @param userDetails Detalles del usuario.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica si un token JWT ha expirado.
     * @param token El token JWT.
     * @return true si el token ha expirado, false en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     * @param token El token JWT.
     * @return La fecha de expiración.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todos los claims del token JWT.
     * @param token El token JWT.
     * @return Todos los claims del token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave de firma para el token JWT.
     * @return La clave de firma.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
