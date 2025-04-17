package org.imaginemos.supplierservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.supplierservice.model.Supplier;
import org.imaginemos.supplierservice.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplierRequestHandlerTest {

    @Mock
    private SupplierRepository repository;

    @InjectMocks
    private SupplierRequestHandler handler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCrearProveedor() throws Exception {
        Supplier mock = new Supplier(1L, "Proveedor1", "123456789","endpar","parque Don Alberto");
        when(repository.save(any())).thenReturn(mock);

        String json = objectMapper.writeValueAsString(Map.of(
                "action", "crear",
                "data", Map.of("nombre", "Proveedor1", "nit", "123456789")
        ));

        String response = handler.handleRequest(json);
        assertTrue(response.contains("Proveedor1"));
    }
}
