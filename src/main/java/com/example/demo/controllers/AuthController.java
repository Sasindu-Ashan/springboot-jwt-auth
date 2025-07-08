package com.example.demo.controllers;

import com.example.demo.dtos.AuthRequest;
import com.example.demo.dtos.AuthResponse;
import com.example.demo.entities.User;
import com.example.demo.services.JwtService;
import com.example.demo.services.LogoutService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final LogoutService logoutService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService, LogoutService logoutService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.logoutService = logoutService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userService.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userService.getByUsername(request.getUsername());
        if (user != null && jwtService.passwordMatch(request.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            return new AuthResponse(token);
        }
        throw new RuntimeException("Invalid Credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        // Delegate logout to the logoutService instance
        return logoutService.logout(authHeader);
    }
}
