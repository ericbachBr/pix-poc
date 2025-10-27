package com.example.pix.integration.impl;

import com.example.pix.dto.AnaliseFraudeResponse;
import com.example.pix.enums.StatusErro;
import com.example.pix.integration.AntiFraudClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AntiFraudClientImpl implements AntiFraudClient {

    @Value("${antifraud.url:http://localhost:8081}")
    private String antifraudUrl;

    private final RestTemplate restTemplate;

    public AntiFraudClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AnaliseFraudeResponse checkFraud(Map<String, Object> payload) {
        String url = antifraudUrl + "/api/v1/antifraud/check";
        try {
            // ✅ Agora pedimos diretamente o objeto AnaliseFraudeResponse
            AnaliseFraudeResponse response = restTemplate.postForObject(url, payload, AnaliseFraudeResponse.class);

            if (response != null && response.getStatus() != null) {
                return response;
            } else {
                return new AnaliseFraudeResponse(
                        StatusErro.PROCESSAMENTO.toString(),
                        "Resposta inválida do serviço antifraude"
                );
            }

        } catch (Exception e) {
            System.err.println("Erro ao consultar serviço de fraude: " + e.getMessage());
            return new AnaliseFraudeResponse(
                    StatusErro.PROCESSAMENTO.toString(),
                    "Falha na integração com antifraude: " + e.getMessage()
            );
        }
    }
}
