package com.example.pix.integration.impl;

import com.example.pix.dto.AnaliseFraudeResponse;
import com.example.pix.enums.StatusErro;
import com.example.pix.enums.StatusFraude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class AntiFraudClientImplTest {

    private AntiFraudClientImpl antiFraudClient;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        antiFraudClient = new AntiFraudClientImpl(restTemplate);

        // Corrige o campo @Value manualmente
        Field campo = AntiFraudClientImpl.class.getDeclaredField("antifraudUrl");
        campo.setAccessible(true);
        campo.set(antiFraudClient, "http://localhost:8081");

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void deveRetornarStatusQuandoRespostaPossuiStatus() {
        String respostaEsperada = """
            {
              "status": "APROVADO",
              "motivo": null
            }
            """;

        mockServer.expect(requestTo("http://localhost:8081/api/v1/antifraud/check"))
                .andRespond(withSuccess(respostaEsperada, MediaType.APPLICATION_JSON));

        AnaliseFraudeResponse resultado = antiFraudClient.checkFraud(Map.of("cpf", "123"));

        assertEquals(StatusFraude.APROVADO.name(), resultado.getStatus());
        assertEquals(null, resultado.getMotivo());
    }

    @Test
    void deveRetornarErroIntegracaoQuandoOcorreExcecao() {
        mockServer.expect(requestTo("http://localhost:8081/api/v1/antifraud/check"))
                .andRespond(withSuccess("invalid-json", MediaType.TEXT_PLAIN));

        AnaliseFraudeResponse resultado = antiFraudClient.checkFraud(Map.of("cpf", "123"));

        assertEquals(StatusErro.PROCESSAMENTO.name(), resultado.getStatus());
        // Opcional: validar que o motivo contém texto de erro
        assertEquals(true, resultado.getMotivo().toLowerCase().contains("falha"));
    }
}
