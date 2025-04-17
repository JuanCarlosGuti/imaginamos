package org.imaginemos.authservice.service;

import org.imaginemos.authservice.model.User;
import org.imaginemos.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String username, String rawPassword, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        String encryptedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User(username, encryptedPassword, role);
        return userRepository.save(newUser);
    }

    public User authenticate(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;
        }

        return user;
    }
}
