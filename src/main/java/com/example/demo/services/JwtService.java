package com.example.demo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final PasswordEncoder passwordEncoder;

    // Secret key for signing JWTs - keep this safe and load from config in production
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token validity in milliseconds (e.g., 1 hour)
    private final long jwtExpirationMs = 3600000;

    public JwtService(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Encode raw password before saving user
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // Check raw password against encoded one
    public boolean passwordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Generate JWT token with username as subject
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    // Validate JWT token: check signature and expiration
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Token invalid or expired
            return false;
        }
    }

    // Extract username (subject) from token
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
