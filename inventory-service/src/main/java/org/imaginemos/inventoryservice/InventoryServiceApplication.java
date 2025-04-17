package org.imaginemos.inventoryservice;

import jakarta.annotation.PostConstruct;
import org.imaginemos.inventoryservice.handler.InventoryRequestHandler;
import org.imaginemos.inventoryservice.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication {

	private final InventoryRequestHandler handler;

	public InventoryServiceApplication(InventoryRequestHandler handler) {
		this.handler = handler;
	}

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@PostConstruct
	public void initTcpServer() {
		new Thread(() -> new TcpServer(4002, handler).start()).start();
	}
}
