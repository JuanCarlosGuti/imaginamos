package org.imaginemos.inventoryservice.tcp;

import org.imaginemos.inventoryservice.handler.InventoryRequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private final int port;
    private final InventoryRequestHandler handler;

    public TcpServer(int port, InventoryRequestHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Server de inventario escuchando en puerto " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String request = reader.readLine();
            String response = handler.handleRequest(request);

            writer.write(response + "\n");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
