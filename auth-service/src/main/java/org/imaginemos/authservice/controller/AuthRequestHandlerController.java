package org.imaginemos.authservice.controller;

import org.imaginemos.authservice.dto.UserResponseDTO;
import org.imaginemos.authservice.model.User;
import org.imaginemos.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRequestHandlerController {

    @Autowired
    private AuthService authService;

    static class LoginRequest {
        public String username;
        public String password;
    }

    static class RegisterRequest {
        public String username;
        public String password;
        public String role;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User created = authService.registerUser(request.username, request.password, request.role);
            created.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponseDTO(created.getId(), created.getUsername(), created.getRole()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El nombre de usuario ya está en uso");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginReq) {
        User user = authService.authenticate(loginReq.username, loginReq.password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }

        return ResponseEntity.ok(
                new UserResponseDTO(user.getId(), user.getUsername(), user.getRole())
        );
    }
}
