package com.example.pix.controller;

import com.example.pix.domain.Auditoria;
import com.example.pix.repository.AuditoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auditorias")
public class AuditoriaController {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaController(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }


    @GetMapping
    public ResponseEntity<Page<Auditoria>> auditorias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(auditoriaRepository.findAll(pageable));
    }


    @GetMapping("/evento")
    public ResponseEntity<Page<Auditoria>> consultarPorEvento(
            @RequestParam String tipoEvento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(auditoriaRepository.findByTipoEvento(tipoEvento, pageable));
    }

    @GetMapping("/agendamento")
    public ResponseEntity<Page<Auditoria>> consultarPorAgendamento(
            @RequestParam String idCorrelacao,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(auditoriaRepository.findByIdCorrelacao(idCorrelacao, pageable));
    }

    @GetMapping("/agendamento-evento")
    public ResponseEntity<Page<Auditoria>> consultarPorAgendamentoEvento(
            @RequestParam String idCorrelacao,
            @RequestParam String tipoEvento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(auditoriaRepository.findByIdCorrelacaoAndTipoEvento(idCorrelacao, tipoEvento, pageable));
    }
}