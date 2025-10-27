package com.example.antifraud.service.impl;

import com.example.antifraud.dto.AnaliseFraudeResponse;
import com.example.antifraud.enums.StatusFraude;
import com.example.antifraud.service.AntiFraudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AntiFraudServiceImplTest {

    private AntiFraudService service;

    @BeforeEach
    void setUp() {
        service = new AntiFraudServiceImpl();
    }

    @Test
    void deveAprovarTransacaoValida() {
        Map<String, Object> payload = Map.of(
                "valor", new BigDecimal("500.00"),
                "chavePagador", "pagador123",
                "chaveRecebedor", "recebedor456",
                "moeda", "BRL"
        );

        AnaliseFraudeResponse resp = service.analisar(payload);

        assertEquals(StatusFraude.APROVADO.name(), resp.getStatus().name());
        assertEquals("Transação aprovada", resp.getMotivo());
    }

    @Test
    void deveRejeitarTransacaoComMesmaChave() {
        Map<String, Object> payload = Map.of(
                "valor", new BigDecimal("500.00"),
                "chavePagador", "chave123",
                "chaveRecebedor", "chave123",
                "moeda", "BRL"
        );

        AnaliseFraudeResponse resp = service.analisar(payload);

        assertEquals(StatusFraude.REJEITADO.name(), resp.getStatus().name());
        assertEquals("Pagador e recebedor não podem ser a mesma chave", resp.getMotivo());
    }

    @Test
    void deveRejeitarTransacaoComMoedaInvalida() {
        Map<String, Object> payload = Map.of(
                "valor", new BigDecimal("500.00"),
                "chavePagador", "pagador123",
                "chaveRecebedor", "recebedor456",
                "moeda", "USD"
        );

        AnaliseFraudeResponse resp = service.analisar(payload);

        assertEquals(StatusFraude.REJEITADO.name(), resp.getStatus().name());
        assertEquals("Somente transações em BRL são permitidas", resp.getMotivo());
    }

    @Test
    void deveRejeitarChavesPixCurta() {
        Map<String, Object> payload = Map.of(
                "valor", new BigDecimal("500.00"),
                "chavePagador", "abc",
                "chaveRecebedor", "xyz",
                "moeda", "BRL"
        );

        AnaliseFraudeResponse resp = service.analisar(payload);

        assertEquals(StatusFraude.REJEITADO.name(), resp.getStatus().name());
        assertEquals("Chaves PIX inválidas (devem conter ao menos 5 caracteres)", resp.getMotivo());
    }

    @Test
    void deveSolicitarRevisaoManualParaValorAlto() {
        Map<String, Object> payload = Map.of(
                "valor", new BigDecimal("15000.00"),
                "chavePagador", "pagador123",
                "chaveRecebedor", "recebedor456",
                "moeda", "BRL"
        );

        AnaliseFraudeResponse resp = service.analisar(payload);

        assertEquals(StatusFraude.REVISAO_MANUAL.name(), resp.getStatus().name());
        assertEquals("Transação acima de R$10.000 requer revisão manual", resp.getMotivo());
    }
}