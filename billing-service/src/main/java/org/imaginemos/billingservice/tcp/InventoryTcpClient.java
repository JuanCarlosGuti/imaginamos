package org.imaginemos.billingservice.tcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Component
public class InventoryTcpClient {

    private final ObjectMapper objectMapper = new ObjectMapper();


    protected Socket createSocket() throws Exception {
        return new Socket("inventory-service", 4002);
    }

    public String sendMessage(String action, Map<String, Object> data) {
        try (Socket socket = createSocket();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Map<String, Object> request = new HashMap<>();
            request.put("action", action);
            request.put("data", data);

            writer.write(objectMapper.writeValueAsString(request) + "\n");
            writer.flush();

            String response = reader.readLine();
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"No se pudo conectar con inventory-service\"}";
        }
    }

    public String actualizarInventario(String nombre, int cantidad) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("cantidad", cantidad);
        return sendMessage("actualizacion", data);
    }

    public String revertirInventario(String nombre, int cantidad) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("cantidad", cantidad);
        return sendMessage("revertir", data);
    }
}
