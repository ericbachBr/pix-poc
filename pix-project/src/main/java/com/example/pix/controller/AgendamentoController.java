package com.example.pix.controller;

import com.example.pix.config.CorrelationIdFilter;
import com.example.pix.dto.AgendamentoRequestDTO;
import com.example.pix.dto.AgendamentoResponseDTO;
import com.example.pix.service.AgendamentoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@Valid @RequestBody AgendamentoRequestDTO req, HttpServletRequest request) {
        String correlationId = (String) request.getAttribute(CorrelationIdFilter.CORRELATION_ID_HEADER);

        AgendamentoResponseDTO resp = service.criarAgendamento(req, correlationId);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> consultarAgendamento(@PathVariable UUID id) {
        AgendamentoResponseDTO resp = service.consultarAgendamento(id);
        return ResponseEntity.ok(resp);
    }


    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentos() {
        List<AgendamentoResponseDTO> lista = service.listarAgendamentos();
        return ResponseEntity.ok(lista);
    }

}