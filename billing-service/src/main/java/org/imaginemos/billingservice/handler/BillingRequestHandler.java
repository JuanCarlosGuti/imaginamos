package org.imaginemos.billingservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.billingservice.model.Invoice;
import org.imaginemos.billingservice.repository.InvoiceRepository;
import org.imaginemos.billingservice.tcp.InventoryTcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BillingRequestHandler {

    @Autowired
    private InvoiceRepository repository;

    @Autowired
    private InventoryTcpClient inventoryTcpClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String handleRequest(String json) {
        try {
            Map<String, Object> request = objectMapper.readValue(json, Map.class);
            String action = (String) request.get("action");
            Map<String, Object> data = (Map<String, Object>) request.get("data");

            Map<String, Object> response = new HashMap<>();

            switch (action) {
                case "crear" -> {
                    Long clientId = ((Number) data.get("clientId")).longValue();
                    double total = ((Number) data.get("total")).doubleValue();
                    String producto = (String) data.get("producto");
                    int cantidad = ((Number) data.get("cantidad")).intValue();

                    Invoice nueva = repository.save(new Invoice(null, clientId, total, new Date()));
                    response.put("message", "Factura registrada");
                    response.put("factura", nueva);

                    // Comunicación con inventory-service
                    String resultadoInventario = inventoryTcpClient.actualizarInventario(producto, cantidad);
                    response.put("inventario", resultadoInventario);
                }
                case "listar" -> {
                    List<Invoice> facturas = repository.findAll();
                    response.put("facturas", facturas);
                }
                default -> response.put("error", "Accion no reconocida");
            }

            return objectMapper.writeValueAsString(response);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Error al procesar solicitud\"}";
        }
    }
}
