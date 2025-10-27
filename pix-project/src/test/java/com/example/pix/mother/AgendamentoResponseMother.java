package com.example.pix.mother;

import com.example.pix.dto.AgendamentoResponseDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AgendamentoResponseMother {

    public static AgendamentoResponseDTO criarAgendamentoResponseDTO(UUID id, String correlationId, String status, String mensagem) {
        return AgendamentoResponseDTO.builder()
                .agendamentoId(id)
                .idCorrelacao(correlationId)
                .status(status)
                .message(mensagem)
                .timestamp(OffsetDateTime.now())
                .chavePagador("pagador-123")
                .nomePagador("Maria")
                .chaveRecebedor("recebedor-456")
                .nomeRecebedor("Loja")
                .moeda("BRL")
                .valor(new BigDecimal("100.00"))
                .periodicidade("UNICA")
                .dataInicio(OffsetDateTime.parse("2024-11-01T10:00:00-03:00"))
                .dataFim(null)
                .motivo(mensagem)
                .criado(OffsetDateTime.now())
                .atualizado(OffsetDateTime.now())
                .build();
    }
}
