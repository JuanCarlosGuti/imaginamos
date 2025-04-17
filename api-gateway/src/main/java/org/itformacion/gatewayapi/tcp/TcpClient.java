package org.itformacion.gatewayapi.tcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


@Component
public class TcpClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String sendMessage(String service, String action, Map<String, Object> data) {
        try (Socket socket = new Socket("auth-service", 4001)) {

            Map<String, Object> message = new HashMap<>();
            message.put("service", service);
            message.put("action", action);
            message.put("data", data);

            String jsonMessage = objectMapper.writeValueAsString(message);

            OutputStream output = socket.getOutputStream();
            output.write((jsonMessage + "\n").getBytes());
            output.flush();


            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("⬅️ Respuesta recibida del microservicio: " + response);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"No se pudo conectar al servicio\"}";
        }
    }
}

