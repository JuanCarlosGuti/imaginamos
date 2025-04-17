package org.imaginemos.billingservice.tcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTcpClientTest {

    private InventoryTcpClient client;
    private ObjectMapper objectMapper;

    // Clase interna para pruebas que redirige la conexión a localhost
    class TestableInventoryTcpClient extends InventoryTcpClient {
        @Override
        protected Socket createSocket() throws IOException {
            return new Socket("localhost", 4002);
        }
    }

    @BeforeEach
    public void setup() {
        client = new TestableInventoryTcpClient();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testActualizarInventario_ResponseOk() throws Exception {
        // Simular el servidor TCP
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(4002)) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String request = reader.readLine();
                assertTrue(request.contains("Teclado"));
                writer.write("{\"message\":\"Stock actualizado\"}\n");
                writer.flush();
            } catch (IOException e) {
                fail("Error en servidor TCP simulado: " + e.getMessage());
            }
        }).start();

        Thread.sleep(500); // Espera para que el servidor arranque

        String response = client.actualizarInventario("Teclado", 2);
        assertTrue(response.contains("Stock actualizado"));
    }

    @Test
    public void testActualizarInventario_SinServidor() {
        // No se levanta servidor aquí para provocar el error
        InventoryTcpClient fallbackClient = new TestableInventoryTcpClient() {
            @Override
            protected Socket createSocket() throws IOException {
                return new Socket("localhost", 9999); // puerto que no existe
            }
        };

        String response = fallbackClient.actualizarInventario("ProductoInexistente", 1);
        assertTrue(response.contains("No se pudo conectar"));
    }
}
