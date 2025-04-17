package org.imaginemos.clientservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imaginemos.clientservice.model.Client;
import org.imaginemos.clientservice.repository.ClientRepository;
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
class ClientRequestHandlerTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientRequestHandler handler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCrearCliente() throws Exception {
        Client mock = new Client(1L, "Juan", "123","7458633","calle 18");
        when(repository.save(any())).thenReturn(mock);

        String json = objectMapper.writeValueAsString(Map.of(
                "action", "crear",
                "data", Map.of("nombre", "Juan", "documento", "123")
        ));

        String response = handler.handleRequest(json);
        assertTrue(response.contains("Juan"));
    }
}