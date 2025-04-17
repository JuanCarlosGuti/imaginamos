package org.itformacion.gatewayapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secure")
public class SecureTestController {

    @GetMapping("/ping")
    public ResponseEntity<String> securePing() {
        return ResponseEntity.ok("✅ Acceso autorizado al endpoint protegido");
    }
}
