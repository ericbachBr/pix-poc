package com.example.antifraud.controller;

import com.example.antifraud.dto.AnaliseFraudeResponse;
import com.example.antifraud.enums.StatusFraude;
import com.example.antifraud.service.AntiFraudService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AntiFraudController.class)
public class AntiFraudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AntiFraudService antiFraudService;

    @Test
    void deveRetornarRespostaDeFraude() throws Exception {
        // Arrange
        AnaliseFraudeResponse response = new AnaliseFraudeResponse(
                StatusFraude.APROVADO,
                "Sem suspeita de fraude"
        );

        Mockito.when(antiFraudService.analisar(Mockito.anyMap())).thenReturn(response);

        String jsonPayload = """
        {
          "valor": 100.00,
          "chavePagador": "pagador-123",
          "chaveRecebedor": "recebedor-456",
          "moeda": "BRL"
        }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/antifraud/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APROVADO"))
                .andExpect(jsonPath("$.motivo").value("Sem suspeita de fraude"));
    }
}