package org.itformacion.gatewayapi.controller;

import org.itformacion.gatewayapi.tcp.ClientTcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClientProxyController {

    @Autowired
    private ClientTcpClient clientTcpClient;

    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(clientTcpClient.sendMessage("crear", body));
    }

    @GetMapping("/listar")
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok(clientTcpClient.sendMessage("listar", Map.of()));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(clientTcpClient.sendMessage("actualizar", body));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(clientTcpClient.sendMessage("eliminar", Map.of("id", id)));
    }
}
