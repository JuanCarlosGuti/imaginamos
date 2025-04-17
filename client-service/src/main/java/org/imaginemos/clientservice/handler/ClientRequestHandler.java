package org.imaginemos.clientservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.clientservice.model.Client;
import org.imaginemos.clientservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ClientRequestHandler {

    @Autowired
    private ClientRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String handleRequest(String json) {
        try {
            Map<String, Object> request = objectMapper.readValue(json, Map.class);
            String action = (String) request.get("action");
            Map<String, Object> data = (Map<String, Object>) request.get("data");

            Map<String, Object> response = new HashMap<>();

            switch (action) {
                case "crear" -> {
                    Client nuevo = new Client(null,
                            (String) data.get("nombre"),
                            (String) data.get("email"),
                            (String) data.get("telefono"),
                            (String) data.get("direccion")
                    );
                    response.put("cliente", repository.save(nuevo));
                }
                case "listar" -> response.put("clientes", repository.findAll());
                case "actualizar" -> {
                    Long id = ((Number) data.get("id")).longValue();
                    Optional<Client> clienteOpt = repository.findById(id);
                    if (clienteOpt.isPresent()) {
                        Client cliente = clienteOpt.get();
                        cliente.setNombre((String) data.get("nombre"));
                        cliente.setEmail((String) data.get("email"));
                        cliente.setTelefono((String) data.get("telefono"));
                        cliente.setDireccion((String) data.get("direccion"));
                        response.put("cliente", repository.save(cliente));
                    } else {
                        response.put("error", "Cliente no encontrado");
                    }
                }
                case "eliminar" -> {
                    Long id = ((Number) data.get("id")).longValue();
                    if (repository.existsById(id)) {
                        repository.deleteById(id);
                        response.put("message", "Cliente eliminado");
                    } else {
                        response.put("error", "Cliente no encontrado");
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

