package com.example.pix.service;

import com.example.pix.dto.AgendamentoRequestDTO;
import com.example.pix.dto.AgendamentoResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

public interface AgendamentoService {

    AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO req , String correlationId);
    AgendamentoResponseDTO consultarAgendamento(UUID id);
    List<AgendamentoResponseDTO> listarAgendamentos();



}