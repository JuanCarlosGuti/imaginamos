package org.imaginemos.billingservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.billingservice.model.Invoice;
import org.imaginemos.billingservice.repository.InvoiceRepository;
import org.imaginemos.billingservice.tcp.InventoryTcpClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;


public class BillingRequestHandlerTcpTest {

    @Mock
    private InvoiceRepository repository;

    @Mock
    private InventoryTcpClient inventoryTcpClient;

    @InjectMocks
    private BillingRequestHandler handler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public BillingRequestHandlerTcpTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleRequest_crearFactura() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("action", "crear");
        Map<String, Object> data = Map.of(
                "clientId", 1,
                "total", 150000,
                "producto", "Silla de Playa",
                "cantidad", 2
        );
        request.put("data", data);

        Invoice mockInvoice = new Invoice(1L, 1L, 150000.0, new Date());
        when(repository.save(any())).thenReturn(mockInvoice);
        when(inventoryTcpClient.actualizarInventario("Silla de Playa", 2))
                .thenReturn("{\"message\": \"Stock actualizado\"}");

        String result = handler.handleRequest(objectMapper.writeValueAsString(request));

        assertTrue(result.contains("Factura registrada"));
        assertTrue(result.contains("Stock actualizado")); // en lugar de verificar el producto directamente
        assertTrue(result.contains("150000")); // total de la factura

        verify(repository, times(1)).save(any());
        verify(inventoryTcpClient, times(1)).actualizarInventario("Silla de Playa", 2);
    }

}

