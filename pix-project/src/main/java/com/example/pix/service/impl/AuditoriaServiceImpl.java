package com.example.pix.service.impl;

import com.example.pix.domain.Auditoria;
import com.example.pix.repository.AuditoriaRepository;
import com.example.pix.service.AuditoriaService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {
    private final AuditoriaRepository repo;

    public AuditoriaServiceImpl(AuditoriaRepository repo) {
        this.repo = repo;
    }

    public Auditoria registrar(String idCorrelacao, String tipoEvento, String statusFraude, String motivoFraude, String payload) {
        Auditoria auditoria = Auditoria.builder()
                .idCorrelacao(idCorrelacao)
                .tipoEvento(tipoEvento)
                .statusFraude(statusFraude)
                .motivoFraude(motivoFraude)
                .payload(payload)
                .dataOcorrencia(OffsetDateTime.now())
                .build();
        return repo.save(auditoria);
    }
}