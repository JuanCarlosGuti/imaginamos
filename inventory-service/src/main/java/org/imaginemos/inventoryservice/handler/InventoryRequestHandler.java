package org.imaginemos.inventoryservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.inventoryservice.model.InventoryItem;
import org.imaginemos.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InventoryRequestHandler {

    @Autowired
    private InventoryRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String handleRequest(String json) {
        try {
            Map<String, Object> request = objectMapper.readValue(json, Map.class);
            String action = (String) request.get("action");
            Map<String, Object> data = (Map<String, Object>) request.get("data");

            Map<String, Object> response = new HashMap<>();

            switch (action) {
                case "listar" -> {
                    List<InventoryItem> items = repository.findAll();
                    response.put("items", items);
                }
                case "crear" -> {
                    String nombre = (String) data.get("nombre");
                    int cantidad = (int) data.get("cantidad");
                    int precio = (int) data.get("precio");
                    InventoryItem nuevo = repository.save(new InventoryItem(null, nombre, cantidad,precio));
                    response.put("message", "Item creado");
                    response.put("item", nuevo);
                }
                case "eliminar" -> {
                    Long id = ((Number) data.get("id")).longValue();
                    repository.deleteById(id);
                    response.put("message", "Item eliminado");
                }
                case "actualizacion" -> {
                    String nombre = (String) data.get("nombre");
                    int cantidad = (int) data.get("cantidad");

                    Optional<InventoryItem> itemOpt = repository.findByNombreIgnoreCase(nombre);

                    if (itemOpt.isPresent()) {
                        InventoryItem item = itemOpt.get();
                        item.setCantidad(item.getCantidad() - cantidad);
                        repository.save(item);
                        response.put("message", "Stock actualizado");
                        response.put("item", item);
                    } else {
                        response.put("error", "Producto no encontrado");
                    }
                }
                default -> response.put("error", "Acción no reconocida");
            }

            return objectMapper.writeValueAsString(response);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Error al procesar solicitud\"}";
        }
    }
}
