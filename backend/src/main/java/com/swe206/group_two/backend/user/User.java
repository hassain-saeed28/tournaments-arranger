package com.swe206.group_two.backend.user;

import com.swe206.group_two.backend.utils.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_password_hash", nullable = false)
    private String passwordHash;

    public User() {
    }

    public User(String name, UserRole role, String email, String passwordHash) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
