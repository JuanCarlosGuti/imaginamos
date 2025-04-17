package org.imaginemos.inventoryservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.inventoryservice.model.InventoryItem;
import org.imaginemos.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryRequestHandlerTest {

    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private InventoryRequestHandler handler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarInventario() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(new InventoryItem(1L, "Producto", 10, 50000)));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"action\":\"listar\",\"data\":{}}";
        String response = handler.handleRequest(json);

        assertTrue(response.contains("Producto"));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testActualizarStockNoExistente() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"action\":\"actualizacion\",\"data\":{\"nombre\":\"Inexistente\",\"cantidad\":1}}";
        String response = handler.handleRequest(json);

        assertTrue(response.contains("Producto no encontrado"));
    }
}
