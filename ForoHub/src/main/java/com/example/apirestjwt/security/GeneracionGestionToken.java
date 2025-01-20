package com.example.apirestjwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.example.apirestjwt.model.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class GeneracionGestionToken {
    @Value("${api.security.token.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            Date fechaCreacion = new Date();
            Date fechaExpiracion = Date.from(generarCADUCIDAD());

            String token = JWT.create()
                    .withIssuer("EMISOR JWT")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withIssuedAt(fechaCreacion)
                    .withExpiresAt(fechaExpiracion)
                    .sign(algorithm);

            // Log de información del token
            System.out.println("\n=== Generación de Token ===");
            System.out.println("Token generado: " + token);
            System.out.println("Fecha de creación: " + formatearFecha(fechaCreacion));
            System.out.println("Fecha de expiración: " + formatearFecha(fechaExpiracion));
            
            return token;
            
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("EMISOR JWT")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println("Error al verificar el token: " + exception.getMessage());
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Token inválido");
        }
        return verifier.getSubject();
    }

    public void verificarToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            
            System.out.println("\n=== Verificación de Token ===");
            System.out.println("Issuer: " + decodedJWT.getIssuer());
            System.out.println("Subject (username): " + decodedJWT.getSubject());
            System.out.println("ID de usuario: " + decodedJWT.getClaim("id").asInt());
            System.out.println("Fecha de creación: " + formatearFecha(decodedJWT.getIssuedAt()));
            System.out.println("Fecha de expiración: " + formatearFecha(decodedJWT.getExpiresAt()));
            
            if (decodedJWT.getExpiresAt().before(new Date())) {
                System.out.println("¡ADVERTENCIA: El token ha expirado!");
            } else {
                System.out.println("El token es válido y no ha expirado.");
            }
            
        } catch (JWTVerificationException exception) {
            System.out.println("Error al verificar el token: " + exception.getMessage());
        }
    }

    private Instant generarCADUCIDAD() {
        Instant expiration = LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-05:00"));
        System.out.println("Fecha de expiración generada: " + expiration);
        return expiration;
    }

    private String formatearFecha(Date fecha) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .format(LocalDateTime.ofInstant(fecha.toInstant(), ZoneOffset.systemDefault()));
    }
}