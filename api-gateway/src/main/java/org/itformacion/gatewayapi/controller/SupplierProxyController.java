package org.itformacion.gatewayapi.controller;

import org.itformacion.gatewayapi.tcp.SupplierTcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/proveedores")
public class SupplierProxyController {

    @Autowired
    private SupplierTcpClient supplierTcpClient;

    @PostMapping("/crear")
    public ResponseEntity<String> crear(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(supplierTcpClient.sendMessage("crear", body));
    }

    @GetMapping("/listar")
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok(supplierTcpClient.sendMessage("listar", Map.of()));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(supplierTcpClient.sendMessage("actualizar", body));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(supplierTcpClient.sendMessage("eliminar", Map.of("id", id)));
    }
}
