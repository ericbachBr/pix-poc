package com.example.pix.service;


import com.example.pix.domain.Auditoria;
import com.example.pix.repository.AuditoriaRepository;
import com.example.pix.service.impl.AuditoriaServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuditoriaServiceTest {

    @Test
    void deveRegistrarAuditoriaComDadosCorretos() {
        AuditoriaRepository repo = mock(AuditoriaRepository.class);
        AuditoriaServiceImpl service = new AuditoriaServiceImpl(repo);

        String idCorrelacao = "rec-001";
        String tipoEvento = "TESTE_EVENTO";
        String statusFraude = "APROVADO";
        String motivoFraude = "Sem fraude";
        String payload = "{\"exemplo\":\"dados\"}";

        Auditoria auditoriaMock = Auditoria.builder()
                .idCorrelacao(idCorrelacao)
                .tipoEvento(tipoEvento)
                .statusFraude(statusFraude)
                .motivoFraude(motivoFraude)
                .payload(payload)
                .dataOcorrencia(OffsetDateTime.now())
                .build();

        when(repo.save(any(Auditoria.class))).thenReturn(auditoriaMock);

        Auditoria result = service.registrar(idCorrelacao, tipoEvento, statusFraude, motivoFraude, payload);

        assertNotNull(result);
        assertEquals(idCorrelacao, result.getIdCorrelacao());
        assertEquals(tipoEvento, result.getTipoEvento());
        assertEquals(statusFraude, result.getStatusFraude());
        assertEquals(motivoFraude, result.getMotivoFraude());
        assertEquals(payload, result.getPayload());
        assertNotNull(result.getDataOcorrencia());

        verify(repo, times(1)).save(any(Auditoria.class));
    }

}