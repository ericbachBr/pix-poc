package com.example.pix.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "auditorias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Auditoria {
    @Id
    @GeneratedValue
    private UUID id;

    private String idCorrelacao;
    private String tipoEvento;
    private String statusFraude;
    private String motivoFraude;
    @Column(length = 2000)
    private String payload;

    private OffsetDateTime dataOcorrencia;
}