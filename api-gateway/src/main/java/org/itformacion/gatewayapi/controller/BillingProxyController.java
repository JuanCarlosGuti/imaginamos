package org.itformacion.gatewayapi.controller;

import org.itformacion.gatewayapi.tcp.BillingTcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/billing")
public class BillingProxyController {

    @Autowired
    private BillingTcpClient billingTcpClient;

    @PostMapping("/crear")
    public ResponseEntity<String> crearFactura(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(billingTcpClient.sendMessage("crear", body));
    }

    @GetMapping("/listar")
    public ResponseEntity<String> listarFacturas() {
        return ResponseEntity.ok(billingTcpClient.sendMessage("listar", Map.of()));
    }

    @PostMapping("/actualizar")
    public ResponseEntity<String> actualizarFactura(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(billingTcpClient.sendMessage("actualizar", body));
    }

    @PostMapping("/eliminar")
    public ResponseEntity<String> eliminarFactura(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(billingTcpClient.sendMessage("eliminar", body));
    }

    @PostMapping("/devolver")
    public ResponseEntity<String> devolverProducto(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(billingTcpClient.sendMessage("devolver", body));
    }

}

