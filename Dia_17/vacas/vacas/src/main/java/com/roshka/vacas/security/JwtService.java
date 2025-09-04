package com.roshka.vacas.security;

import com.roshka.vacas.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:3600000}") long expirationMs
    ) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generateToken(Usuario u) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(u.getCorreo())
                .issuedAt(now)
                .expiration(exp)
                .claim("rol", u.getRol() != null ? u.getRol().getNombre() : null)
                .claim("cargo", u.getCargo() != null ? u.getCargo().getNombre() : null)
                .claim("equipo", u.getEquipo() != null ? u.getEquipo().getNombre() : null)
                .claim("diasVacaciones", u.getDiasVacaciones())
                .claim("diasVacacionesRestante", u.getDiasVacacionesRestante())
                .claim("nombre", u.getNombre())
                .claim("apellido", u.getApellido())
                .claim("telefono", u.getTelefono())
                .claim("fechaIngreso", u.getFechaIngreso() != null ? u.getFechaIngreso().toString() : null)
                .signWith(key)
                .compact();
    }


    public String extractSubject(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
