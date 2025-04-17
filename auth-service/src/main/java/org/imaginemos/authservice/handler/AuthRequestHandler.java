package org.imaginemos.authservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.authservice.dto.UserResponseDTO;
import org.imaginemos.authservice.model.User;
import org.imaginemos.authservice.service.AuthService;
import org.imaginemos.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthRequestHandler {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String handleRequest(String json) {
        try {
            Map<String, Object> request = objectMapper.readValue(json, Map.class);
            String action = (String) request.get("action");
            Map<String, Object> data = (Map<String, Object>) request.get("data");

            Map<String, Object> response = new HashMap<>();

            switch (action) {
                case "login" -> {
                    String username = (String) data.get("username");
                    String password = (String) data.get("password");

                    User user = authService.authenticate(username, password);
                    if (user != null) {
                        UserResponseDTO dto = new UserResponseDTO(user.getId(), user.getUsername(), user.getRole());
                        String token = jwtUtil.generateToken(user.getUsername());

                        response.put("message", "Login exitoso");
                        response.put("user", dto);
                        response.put("token", token);
                    } else {
                        response.put("error", "Credenciales incorrectas");
                    }
                }
                case "register" -> {
                    String username = (String) data.get("username");
                    String password = (String) data.get("password");
                    String role = (String) data.get("role");

                    try {
                        User user = authService.registerUser(username, password, role);
                        UserResponseDTO dto = new UserResponseDTO(user.getId(), user.getUsername(), user.getRole());
                        response.put("message", "Usuario registrado");
                        response.put("user", dto);
                    } catch (IllegalArgumentException e) {
                        response.put("error", e.getMessage());
                    }
                }
                default -> response.put("error", "Acción no reconocida");
            }

            return objectMapper.writeValueAsString(response);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Error al procesar la solicitud\"}";
        }
    }
}
