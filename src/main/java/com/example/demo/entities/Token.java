package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public boolean isRevoked() {
        return false;
    }

    public void setToken(String token) {
    }

    public void setUser(String user) {
    }

    public void setRevoked(boolean b) {
    }
}

