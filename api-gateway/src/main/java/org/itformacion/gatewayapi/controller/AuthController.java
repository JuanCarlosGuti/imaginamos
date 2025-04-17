package org.itformacion.gatewayapi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.itformacion.gatewayapi.dto.LoginRequest;
import org.itformacion.gatewayapi.tcp.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TcpClient tcpClient;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String result = tcpClient.sendMessage("auth", "login", Map.of(
                "username", request.getUsername(),
                "password", request.getPassword()
        ));

        System.out.println("⬅️ Respuesta recibida del microservicio: " + result);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> parsed = mapper.readValue(result, Map.class);

            if (parsed.containsKey("error")) {
                return ResponseEntity.status(401).body(result);
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Respuesta inválida del microservicio\"}");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                tcpClient.sendMessage("auth", "register", Map.of(
                        "username", request.getUsername(),
                        "password", request.getPassword(),
                        "role", request.getRole()
                ))
        );
    }
}

