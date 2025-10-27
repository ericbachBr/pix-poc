package com.example.pix.controller;

import com.example.pix.dto.AgendamentoRequestDTO;
import com.example.pix.mother.AgendamentoResponseMother;
import com.example.pix.service.AgendamentoService;
import com.example.pix.dto.AgendamentoResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendamentoController.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendamentoService agendamentoService;


    @Test
    void deveCriarAgendamento() throws Exception {
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

        UUID id = UUID.randomUUID();
        String correlationId = "rec-001";

        AgendamentoResponseDTO resp = AgendamentoResponseMother.criarAgendamentoResponseDTO(
                id,
                correlationId,
                "APROVADO",
                "Agendamento criado com sucesso"
        );



        Mockito.when(agendamentoService.criarAgendamento(Mockito.any(AgendamentoRequestDTO.class), Mockito.anyString()))
                .thenReturn(resp);

        String json = """
        {
          "valor": 100.00,
          "moeda": "BRL",
          "chavePagador": "pagador-123",
          "nomePagador": "Maria",
          "chaveRecebedor": "recebedor-456",
          "nomeRecebedor": "Loja",
          "periodicidade": "UNICA",
          "dataInicio": "2024-11-01T10:00:00-03:00"
        }
        """;

        mockMvc.perform(post("/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agendamentoId").value(id.toString()))
                .andExpect(jsonPath("$.status").value("APROVADO"))
                .andExpect(jsonPath("$.chavePagador").value("pagador-123"))
                .andExpect(jsonPath("$.nomePagador").value("Maria"))
                .andExpect(jsonPath("$.valor").value(100.00));

    }


    @Test
    void deveListarAgendamentos() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        AgendamentoResponseDTO resp1 = AgendamentoResponseMother
                .criarAgendamentoResponseDTO(id1, "rec-001", "APROVADO", "Agendamento criado");

        AgendamentoResponseDTO resp2 = AgendamentoResponseMother
                .criarAgendamentoResponseDTO(id2, "rec-002", "PENDENTE", "Agendamento criado");

        Mockito.when(agendamentoService.listarAgendamentos()).thenReturn(List.of(resp1, resp2));

        mockMvc.perform(get("/v1/agendamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].agendamentoId").value(id1.toString()))
                .andExpect(jsonPath("$[1].agendamentoId").value(id2.toString()))
                .andExpect(jsonPath("$[0].status").value("APROVADO"))
                .andExpect(jsonPath("$[1].status").value("PENDENTE"))
                .andExpect(jsonPath("$[0].chavePagador").value("pagador-123"))
                .andExpect(jsonPath("$[1].chavePagador").value("pagador-123"))
                .andExpect(jsonPath("$[0].nomePagador").value("Maria"))
                .andExpect(jsonPath("$[1].nomePagador").value("Maria"))
                .andExpect(jsonPath("$[0].valor").value(100.00))
                .andExpect(jsonPath("$[1].valor").value(100.00));

    }


    @Test
    void deveConsultarAgendamentoPorId() throws Exception {
        UUID id = UUID.randomUUID();
        AgendamentoResponseDTO resp = AgendamentoResponseMother
                .criarAgendamentoResponseDTO(id, "rec-001", "APROVADO", "Consulta realizada");
        Mockito.when(agendamentoService.consultarAgendamento(id)).thenReturn(resp);


        mockMvc.perform(get("/v1/agendamentos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agendamentoId").value(id.toString()))
                .andExpect(jsonPath("$.status").value("APROVADO"));
    }




}