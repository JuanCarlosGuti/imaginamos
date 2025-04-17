package org.imaginemos.supplierservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.supplierservice.model.Supplier;
import org.imaginemos.supplierservice.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SupplierRequestHandler {

    @Autowired
    private SupplierRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String handleRequest(String json) {
        try {
            Map<String, Object> request = objectMapper.readValue(json, Map.class);
            String action = (String) request.get("action");
            Map<String, Object> data = (Map<String, Object>) request.get("data");

            Map<String, Object> response = new HashMap<>();

            switch (action) {
                case "crear" -> {
                    Supplier nuevo = new Supplier(null,
                            (String) data.get("nombre"),
                            (String) data.get("email"),
                            (String) data.get("telefono"),
                            (String) data.get("empresa")
                    );
                    response.put("proveedor", repository.save(nuevo));
                }
                case "listar" -> response.put("proveedores", repository.findAll());
                case "actualizar" -> {
                    Long id = ((Number) data.get("id")).longValue();
                    Optional<Supplier> proveedorOpt = repository.findById(id);
                    if (proveedorOpt.isPresent()) {
                        Supplier proveedor = proveedorOpt.get();
                        proveedor.setNombre((String) data.get("nombre"));
                        proveedor.setEmail((String) data.get("email"));
                        proveedor.setTelefono((String) data.get("telefono"));
                        proveedor.setEmpresa((String) data.get("empresa"));
                        response.put("proveedor", repository.save(proveedor));
                    } else {
                        response.put("error", "Proveedor no encontrado");
                    }
                }
                case "eliminar" -> {
                    Long id = ((Number) data.get("id")).longValue();
                    if (repository.existsById(id)) {
                        repository.deleteById(id);
                        response.put("message", "Proveedor eliminado");
                    } else {
                        response.put("error", "Proveedor no encontrado");
                    }
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
