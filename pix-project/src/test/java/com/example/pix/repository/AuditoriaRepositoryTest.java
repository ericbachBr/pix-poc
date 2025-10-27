package com.example.pix.repository;

import com.example.pix.domain.Auditoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuditoriaRepositoryTest {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Test
    void deveSalvarEAcharPorTipoEvento() {
        Auditoria auditoria = Auditoria.builder()
                .idCorrelacao("rec-001")
                .tipoEvento("TESTE")
                .statusFraude("APROVADO")
                .motivoFraude("Sem fraude")
                .payload("{\"exemplo\":\"dados\"}")
                .dataOcorrencia(OffsetDateTime.now())
                .build();

        auditoriaRepository.save(auditoria);

        assertFalse(
                auditoriaRepository.findByTipoEvento("TESTE", PageRequest.of(0, 10)).isEmpty(),
                "Deve retornar auditoria para o tipo de evento TESTE"
        );
    }
}
