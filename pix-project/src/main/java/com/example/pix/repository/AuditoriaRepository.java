package com.example.pix.repository;

import com.example.pix.domain.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditoriaRepository extends JpaRepository<Auditoria, UUID> {

    Page<Auditoria> findByTipoEvento(String tipoEvento, Pageable pageable);
    Page<Auditoria> findByIdCorrelacao(String idCorrelacao, Pageable pageable);
    Page<Auditoria> findByIdCorrelacaoAndTipoEvento(String idCorrelacao, String tipoEvento, Pageable pageable);

}