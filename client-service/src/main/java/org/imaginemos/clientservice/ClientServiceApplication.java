package org.imaginemos.clientservice;

import jakarta.annotation.PostConstruct;
import org.imaginemos.clientservice.handler.ClientRequestHandler;
import org.imaginemos.clientservice.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientServiceApplication {

    private final ClientRequestHandler handler;

    public ClientServiceApplication(ClientRequestHandler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @PostConstruct
    public void startTcpServer() {
        new Thread(() -> new TcpServer(4004, handler).start()).start();
    }
}
