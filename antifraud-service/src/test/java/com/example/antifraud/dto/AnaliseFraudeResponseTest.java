package com.example.antifraud.dto;

import com.example.antifraud.enums.StatusFraude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnaliseFraudeResponseTest {

    @Test
    void deveCriarObjetoComConstrutorCompleto() {
        AnaliseFraudeResponse response = new AnaliseFraudeResponse(StatusFraude.APROVADO, "Tudo certo");

        assertEquals(StatusFraude.APROVADO, response.getStatus());
        assertEquals("Tudo certo", response.getMotivo());
    }

    @Test
    void deveCriarObjetoComConstrutorVazioESetters() {
        AnaliseFraudeResponse response = new AnaliseFraudeResponse();
        response.setStatus(StatusFraude.REJEITADO);
        response.setMotivo("Erro detectado");

        assertEquals(StatusFraude.REJEITADO, response.getStatus());
        assertEquals("Erro detectado", response.getMotivo());
    }

    @Test
    void deveTestarEqualsEHashCode() {
        AnaliseFraudeResponse r1 = new AnaliseFraudeResponse(StatusFraude.REJEITADO, "Erro");
        AnaliseFraudeResponse r2 = new AnaliseFraudeResponse(StatusFraude.REJEITADO, "Erro");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void deveTestarToString() {
        AnaliseFraudeResponse response = new AnaliseFraudeResponse(StatusFraude.REVISAO_MANUAL, "Verificação necessária");

        String result = response.toString();

        assertTrue(result.contains("REVISAO_MANUAL"));
        assertTrue(result.contains("Verificação necessária"));
    }
}