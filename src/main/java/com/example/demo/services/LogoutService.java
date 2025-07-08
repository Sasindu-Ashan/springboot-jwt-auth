package com.example.demo.services;

import com.example.demo.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private static final TokenRepository tokenRepository = null;

    public static ResponseEntity<?> logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header.");
        }

        String token = authHeader.substring(7);

        return tokenRepository.findByToken(token).map(t -> {
            t.setRevoked(true);
            tokenRepository.save(t);
            return ResponseEntity.ok("Logout successful. Token has been revoked.");
        }).orElse(ResponseEntity.badRequest().body("Token not found."));
    }
}

