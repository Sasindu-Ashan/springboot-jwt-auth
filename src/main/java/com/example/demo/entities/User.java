package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Entity
@Table(name = "app_user")  // Avoid reserved keyword 'user'
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> o = null;
        return o;
    }
}
