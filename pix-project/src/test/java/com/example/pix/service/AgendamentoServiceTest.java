package com.example.pix.service;

import com.example.pix.domain.Agendamento;
import com.example.pix.dto.AgendamentoRequestDTO;
import com.example.pix.dto.AgendamentoResponseDTO;
import com.example.pix.dto.AnaliseFraudeResponse;
import com.example.pix.enums.StatusFraude;
import com.example.pix.integration.AntiFraudClient;
import com.example.pix.repository.AgendamentoRepository;
import com.example.pix.service.impl.AgendamentoServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoServiceTest {

    @Test
    void deveCriarAgendamentoComStatusAprovado() {
        // Arrange
        AgendamentoRepository repo = mock(AgendamentoRepository.class);
        AntiFraudClient antiFraudClient = mock(AntiFraudClient.class);
        AuditoriaService auditoriaService = mock(AuditoriaService.class);

        AgendamentoService service = new AgendamentoServiceImpl(repo, antiFraudClient, auditoriaService);

        AgendamentoRequestDTO req = new AgendamentoRequestDTO();
        req.setValor(new BigDecimal("100.00"));
        req.setMoeda("BRL");
        req.setChavePagador("pagador-123");
        req.setNomePagador("Maria");
        req.setChaveRecebedor("recebedor-456");
        req.setNomeRecebedor("Loja");
        req.setPeriodicidade("UNICA");
        req.setDataInicio(OffsetDateTime.now());
        req.setDataFim(null);

        when(antiFraudClient.checkFraud(anyMap()))
                .thenReturn(new AnaliseFraudeResponse(StatusFraude.APROVADO.name(), null));

        Agendamento agendamentoSalvo = Agendamento.builder()
                .id(UUID.randomUUID())
                .idCorrelacao("corr-001")
                .valor(req.getValor())
                .moeda(req.getMoeda())
                .chavePagador(req.getChavePagador())
                .nomePagador(req.getNomePagador())
                .chaveRecebedor(req.getChaveRecebedor())
                .nomeRecebedor(req.getNomeRecebedor())
                .periodicidade(req.getPeriodicidade())
                .dataInicio(req.getDataInicio())
                .dataFim(req.getDataFim())
                .status("APROVADO")
                .criado(OffsetDateTime.now())
                .atualizado(OffsetDateTime.now())
                .build();

        when(repo.save(any(Agendamento.class))).thenReturn(agendamentoSalvo);

        // Act
        AgendamentoResponseDTO resp = service.criarAgendamento(req,"corr-001");

        // Assert
        assertNotNull(resp);
        assertEquals("APROVADO", resp.getStatus());
        assertEquals("corr-001", resp.getIdCorrelacao());
        verify(antiFraudClient, times(1)).checkFraud(anyMap());
        verify(repo, times(1)).save(any(Agendamento.class));
        verify(auditoriaService, atLeastOnce()).registrar(
                eq("corr-001"),
                anyString(),
                anyString(),
                anyString(),
                anyString()
        );
    }


    @Test
    void deveConsultarAgendamentoPorId() {
        // Arrange
        AgendamentoRepository repo = mock(AgendamentoRepository.class);
        AntiFraudClient antiFraudClient = mock(AntiFraudClient.class);
        AuditoriaService auditoriaService = mock(AuditoriaService.class);

        AgendamentoService service = new AgendamentoServiceImpl(repo, antiFraudClient, auditoriaService);

        UUID id = UUID.randomUUID();
        Agendamento ag = Agendamento.builder()
                .id(id)
                .idCorrelacao("rec-002")
                .status("APROVADO")
                .atualizado(OffsetDateTime.now())
                .build();

        when(repo.findById(id)).thenReturn(java.util.Optional.of(ag));

        // Act
        AgendamentoResponseDTO resp = service.consultarAgendamento(id);

        // Assert
        assertNotNull(resp);
        assertEquals(id, resp.getAgendamentoId());
        assertEquals("rec-002", resp.getIdCorrelacao());
        assertEquals("APROVADO", resp.getStatus());
        assertEquals("Consulta realizada", resp.getMessage());
    }

    @Test
    void deveListarTodosAgendamentos() {
        // Arrange
        AgendamentoRepository repo = mock(AgendamentoRepository.class);
        AntiFraudClient antiFraudClient = mock(AntiFraudClient.class);
        AuditoriaService auditoriaService = mock(AuditoriaService.class);

        AgendamentoService service = new AgendamentoServiceImpl(repo, antiFraudClient, auditoriaService);

        Agendamento ag1 = Agendamento.builder()
                .id(UUID.randomUUID())
                .idCorrelacao("rec-003")
                .status("APROVADO")
                .atualizado(OffsetDateTime.now())
                .build();

        Agendamento ag2 = Agendamento.builder()
                .id(UUID.randomUUID())
                .idCorrelacao("rec-004")
                .status("REJEITADO")
                .atualizado(OffsetDateTime.now())
                .build();

        when(repo.findAll()).thenReturn(List.of(ag1, ag2));

        // Act
        List<AgendamentoResponseDTO> lista = service.listarAgendamentos();

        // Assert
        assertEquals(2, lista.size());
        assertEquals("rec-003", lista.get(0).getIdCorrelacao());
        assertEquals("rec-004", lista.get(1).getIdCorrelacao());
        assertEquals("Consulta realizada", lista.get(0).getMessage());
    }

    @Test
    void deveCriarAgendamentoComStatusRejeitado() {
        // Arrange
        AgendamentoRepository repo = mock(AgendamentoRepository.class);
        AntiFraudClient antiFraudClient = mock(AntiFraudClient.class);
        AuditoriaService auditoriaService = mock(AuditoriaService.class);

        AgendamentoService service = new AgendamentoServiceImpl(repo, antiFraudClient, auditoriaService);

        AgendamentoRequestDTO req = new AgendamentoRequestDTO();
        req.setValor(new BigDecimal("100.00"));
        req.setMoeda("BRL");
        req.setChavePagador("pagador-123");
        req.setNomePagador("Maria");
        req.setChaveRecebedor("recebedor-456");
        req.setNomeRecebedor("Loja");
        req.setPeriodicidade("UNICA");
        req.setDataInicio(OffsetDateTime.now());

        when(antiFraudClient.checkFraud(anyMap()))
                .thenReturn(new AnaliseFraudeResponse(StatusFraude.REJEITADO.name(), "SUSPEITA_FRAUDE"));

        Agendamento agendamentoSalvo = Agendamento.builder()
                .id(UUID.randomUUID())
                .idCorrelacao("corr-002")
                .valor(req.getValor())
                .moeda(req.getMoeda())
                .chavePagador(req.getChavePagador())
                .nomePagador(req.getNomePagador())
                .chaveRecebedor(req.getChaveRecebedor())
                .nomeRecebedor(req.getNomeRecebedor())
                .periodicidade(req.getPeriodicidade())
                .dataInicio(req.getDataInicio())
                .status("REJEITADO")
                .criado(OffsetDateTime.now())
                .atualizado(OffsetDateTime.now())
                .build();

        when(repo.save(any(Agendamento.class))).thenReturn(agendamentoSalvo);

        // Act
        AgendamentoResponseDTO resp = service.criarAgendamento(req, "corr-002");

        // Assert
        assertEquals("REJEITADO", resp.getStatus());
        verify(auditoriaService, atLeastOnce()).registrar(eq("corr-002"), anyString(), anyString(), anyString(), anyString());
    }


}