package com.tuckersoft.branchengine.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Firma y validacion de los JWT.
 *
 * TODO [PARTE 1 - SEGURIDAD]: implementar los dos metodos.
 *
 * Reglas que no se negocian:
 *  - Algoritmo HMAC con la clave de jwt.secret (minimo 32 caracteres).
 *  - El token lleva SOLO el email como subject. El rol NUNCA va dentro del
 *    token: se lee de la base de datos en cada peticion, via
 *    CustomUserDetailsService. Si metes el rol en el token, un usuario recien
 *    promovido seguiria siendo ROLE_USER con su token de antes y la estrella 1
 *    falla.
 *  - emailDelToken debe lanzar excepcion si la firma o la fecha no son validas:
 *    JwtAuthFilter ya la captura y responde 401.
 */
@Service
public class JwtService {

    private final SecretKey clave;
    private final long expiracionMs;

    public JwtService(@Value("${jwt.secret}") String secreto,
                      @Value("${jwt.expiration-ms:7200000}") long expiracionMs) {
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMs = expiracionMs;
    }

    public String generarToken(String email) {
        throw new UnsupportedOperationException(
                "TODO [PARTE 1]: firmar el token con el email como subject, iat, exp y la clave");
    }

    /** Devuelve el email del token. Lanza excepcion si la firma o la fecha no son validas. */
    public String emailDelToken(String token) {
        throw new UnsupportedOperationException(
                "TODO [PARTE 1]: verificar la firma y devolver el subject del token");
    }
}
