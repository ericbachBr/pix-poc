package com.example.pix.controller;

import com.example.pix.domain.Auditoria;
import com.example.pix.repository.AuditoriaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuditoriaController.class)
class AuditoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditoriaRepository auditoriaRepository;

    private Auditoria buildAuditoria(String tipoEvento, String idCorrelacao, String statusFraude, String motivoFraude) {
        return Auditoria.builder()
                .id(UUID.randomUUID())
                .idCorrelacao(idCorrelacao)
                .tipoEvento(tipoEvento)
                .statusFraude(statusFraude)
                .motivoFraude(motivoFraude)
                .payload("{\"exemplo\":\"dados\"}")
                .dataOcorrencia(OffsetDateTime.now())
                .build();
    }

    @Test
    void deveListarTodasAuditorias() throws Exception {
        Page<Auditoria> page = new PageImpl<>(List.of(buildAuditoria("EVENTO1", "rec-001", "APROVADO", "Sem fraude")));
        Mockito.when(auditoriaRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/auditorias?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].tipoEvento").value("EVENTO1"))
                .andExpect(jsonPath("$.content[0].idCorrelacao").value("rec-001"))
                .andExpect(jsonPath("$.content[0].statusFraude").value("APROVADO"))
                .andExpect(jsonPath("$.content[0].motivoFraude").value("Sem fraude"));
    }

    @Test
    void deveListarAuditoriasPorEvento() throws Exception {
        Page<Auditoria> page = new PageImpl<>(List.of(buildAuditoria("AGENDAMENTO_CRIADO", "rec-002", "APROVADO", "Sem fraude")));
        Mockito.when(auditoriaRepository.findByTipoEvento(Mockito.eq("AGENDAMENTO_CRIADO"), Mockito.any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/auditorias/evento")
                        .param("tipoEvento", "AGENDAMENTO_CRIADO")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].tipoEvento").value("AGENDAMENTO_CRIADO"))
                .andExpect(jsonPath("$.content[0].idCorrelacao").value("rec-002"))
                .andExpect(jsonPath("$.content[0].statusFraude").value("APROVADO"))
                .andExpect(jsonPath("$.content[0].motivoFraude").value("Sem fraude"));
    }

    @Test
    void deveListarAuditoriasPorAgendamento() throws Exception {
        Page<Auditoria> page = new PageImpl<>(List.of(buildAuditoria("EVENTO2", "rec-003", "REJEITADO", "Valor acima do limite")));
        Mockito.when(auditoriaRepository.findByIdCorrelacao(Mockito.eq("rec-003"), Mockito.any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/auditorias/agendamento")
                        .param("idCorrelacao", "rec-003")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idCorrelacao").value("rec-003"))
                .andExpect(jsonPath("$.content[0].tipoEvento").value("EVENTO2"))
                .andExpect(jsonPath("$.content[0].statusFraude").value("REJEITADO"))
                .andExpect(jsonPath("$.content[0].motivoFraude").value("Valor acima do limite"));
    }

    @Test
    void deveListarAuditoriasPorAgendamentoEEvento() throws Exception {
        Page<Auditoria> page = new PageImpl<>(List.of(buildAuditoria("EVENTO3", "rec-004", "PENDENTE", "Em análise")));
        Mockito.when(auditoriaRepository.findByIdCorrelacaoAndTipoEvento(
                        Mockito.eq("rec-004"), Mockito.eq("EVENTO3"), Mockito.any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/auditorias/agendamento-evento")
                        .param("idCorrelacao", "rec-004")
                        .param("tipoEvento", "EVENTO3")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idCorrelacao").value("rec-004"))
                .andExpect(jsonPath("$.content[0].tipoEvento").value("EVENTO3"))
                .andExpect(jsonPath("$.content[0].statusFraude").value("PENDENTE"))
                .andExpect(jsonPath("$.content[0].motivoFraude").value("Em análise"));
    }
}
