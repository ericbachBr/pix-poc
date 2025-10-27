package com.example.pix.service;


import com.example.pix.domain.Auditoria;

public interface AuditoriaService {
    Auditoria registrar(String idCorrelacao, String tipoEvento, String statusFraude, String motivoFraude, String payload);
}

