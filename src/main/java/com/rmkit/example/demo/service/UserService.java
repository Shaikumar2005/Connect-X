package com.rmkit.example.demo.service;

import com.rmkit.example.demo.entity.UserEntity;
import com.rmkit.example.demo.model.User;
import com.rmkit.example.demo.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public void register(User user) {
        if (repo.existsByUsernameIgnoreCase(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (repo.existsByEmailIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        UserEntity e = new UserEntity();
        e.setUsername(user.getUsername().trim());
        e.setEmail(user.getEmail().trim().toLowerCase());
        e.setPasswordHash(encoder.encode(user.getPassword())); // hash pwd
        repo.save(e);
    }

    public boolean validateLogin(String username, String rawPassword) {
        return repo.findByUsernameIgnoreCase(username)
                .map(u -> encoder.matches(rawPassword, u.getPasswordHash()))
                .orElse(false);
    }
}