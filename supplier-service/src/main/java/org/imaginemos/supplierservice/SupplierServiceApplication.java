package org.imaginemos.supplierservice;

import jakarta.annotation.PostConstruct;
import org.imaginemos.supplierservice.handler.SupplierRequestHandler;
import org.imaginemos.supplierservice.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class SupplierServiceApplication {

    private final SupplierRequestHandler handler;

    @Value("${supplier.tcp.port:4005}")
    private int tcpPort;

    public SupplierServiceApplication(SupplierRequestHandler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {
        SpringApplication.run(SupplierServiceApplication.class, args);
    }

    @PostConstruct
    public void startTcpServer() {
        new Thread(() -> new TcpServer(tcpPort, handler).start()).start();
    }
}
