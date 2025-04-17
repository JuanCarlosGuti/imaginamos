package org.imaginemos.authservice;

import jakarta.annotation.PostConstruct;
import org.imaginemos.authservice.handler.AuthRequestHandler;
import org.imaginemos.authservice.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

    private final AuthRequestHandler handler;

    public AuthServiceApplication(AuthRequestHandler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @PostConstruct
    public void startTcp() {
        new Thread(() -> new TcpServer(4001, handler).start()).start();
    }
}
