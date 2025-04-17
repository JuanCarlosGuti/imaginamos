package org.imaginemos.billingservice;

import jakarta.annotation.PostConstruct;
import org.imaginemos.billingservice.handler.BillingRequestHandler;
import org.imaginemos.billingservice.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillingServiceApplication {

    private final BillingRequestHandler handler;

    public BillingServiceApplication(BillingRequestHandler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @PostConstruct
    public void startTcpServer() {
        new Thread(() -> new TcpServer(4003, handler).start()).start();
    }
}
