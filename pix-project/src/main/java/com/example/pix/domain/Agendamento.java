package com.example.pix.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "agendamentos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agendamento {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String idCorrelacao;

    private String chavePagador;
    private String nomePagador;
    private String chaveRecebedor;
    private String nomeRecebedor;
    private String moeda;
    private java.math.BigDecimal valor;

    private String periodicidade;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private String status;
    private String motivo;
    private OffsetDateTime criado;
    private OffsetDateTime atualizado;
}
