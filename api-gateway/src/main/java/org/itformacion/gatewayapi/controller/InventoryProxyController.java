package org.itformacion.gatewayapi.controller;

import org.itformacion.gatewayapi.tcp.InventoryTcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryProxyController {

    @Autowired
    private InventoryTcpClient tcpClient;

    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(tcpClient.sendMessage("crear", body));
    }

    @GetMapping("/listar")
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok(tcpClient.sendMessage("listar", Map.of()));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(tcpClient.sendMessage("eliminar", Map.of("id", id)));
    }
}
