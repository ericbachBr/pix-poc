
package com.example.pix.dto;

import com.example.pix.mother.AgendamentoResponseMother;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoResponseDTOTest {

    @Test
    void deveUsarConstrutorSemArgumentosESetters() {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();

        UUID id = UUID.randomUUID();
        OffsetDateTime timestamp = OffsetDateTime.now();

        dto.setAgendamentoId(id);
        dto.setIdCorrelacao("rec-001");
        dto.setStatus("APROVADO");
        dto.setMessage("Agendamento criado com sucesso");
        dto.setTimestamp(timestamp);

        assertEquals(id, dto.getAgendamentoId());
        assertEquals("rec-001", dto.getIdCorrelacao());
        assertEquals("APROVADO", dto.getStatus());
        assertEquals("Agendamento criado com sucesso", dto.getMessage());
        assertEquals(timestamp, dto.getTimestamp());
    }

    @Test
    void deveUsarConstrutorComArgumentos() {
        UUID id = UUID.randomUUID();
        OffsetDateTime timestamp = OffsetDateTime.now();

        AgendamentoResponseDTO dto = AgendamentoResponseMother
                .criarAgendamentoResponseDTO(
                id,
                "rec-002",
                "REJEITADO",
                "Erro na validação"
        );

        assertEquals(id, dto.getAgendamentoId());
        assertEquals("rec-002", dto.getIdCorrelacao());
        assertEquals("REJEITADO", dto.getStatus());
        assertEquals("Erro na validação", dto.getMessage());
    }
}
