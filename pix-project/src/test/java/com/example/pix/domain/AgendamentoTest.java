package com.example.pix.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoTest {

    @Test
    void deveCriarAgendamentoComBuilder() {
        UUID id = UUID.randomUUID();
        OffsetDateTime agora = OffsetDateTime.now();

        Agendamento ag = Agendamento.builder()
                .id(id)
                .idCorrelacao("rec-001")
                .chavePagador("chave-pagador")
                .nomePagador("João")
                .chaveRecebedor("chave-recebedor")
                .nomeRecebedor("Maria")
                .moeda("BRL")
                .valor(new BigDecimal("100.00"))
                .periodicidade("MENSAL")
                .dataInicio(agora)
                .dataFim(agora.plusMonths(1))
                .status("APROVADO")
                .criado(agora)
                .atualizado(agora)
                .build();

        assertEquals(id, ag.getId());
        assertEquals("rec-001", ag.getIdCorrelacao());
        assertEquals("João", ag.getNomePagador());
        assertEquals("Maria", ag.getNomeRecebedor());
        assertEquals("BRL", ag.getMoeda());
        assertEquals(new BigDecimal("100.00"), ag.getValor());
        assertEquals("MENSAL", ag.getPeriodicidade());
        assertEquals("APROVADO", ag.getStatus());
        assertEquals(agora, ag.getCriado());
        assertEquals(agora, ag.getAtualizado());
        assertEquals("chave-pagador", ag.getChavePagador());
        assertEquals("chave-recebedor", ag.getChaveRecebedor());
    }

    @Test
    void devePermitirAlterarCamposComSetters() {
        Agendamento ag = new Agendamento();
        ag.setIdCorrelacao("rec-002");
        ag.setStatus("PENDENTE");

        assertEquals("rec-002", ag.getIdCorrelacao());
        assertEquals("PENDENTE", ag.getStatus());
    }

    @Test
    void deveCompararAgendamentosComMesmoId() {
        UUID id = UUID.randomUUID();
        Agendamento ag1 = Agendamento.builder().id(id).build();
        Agendamento ag2 = Agendamento.builder().id(id).build();

        assertEquals(ag1.getId(), ag2.getId());
    }

    @Test
    void deveAceitarValorNuloNosCamposOpcionais() {
        Agendamento ag = Agendamento.builder()
                .idCorrelacao("rec-003")
                .valor(null)
                .moeda(null)
                .build();

        assertNull(ag.getValor());
        assertNull(ag.getMoeda());
    }

    @Test
    void deveValidarDatasCorretamente() {
        OffsetDateTime inicio = OffsetDateTime.now();
        OffsetDateTime fim = inicio.plusDays(10);

        Agendamento ag = Agendamento.builder()
                .dataInicio(inicio)
                .dataFim(fim)
                .build();

        assertTrue(ag.getDataFim().isAfter(ag.getDataInicio()));
    }
}