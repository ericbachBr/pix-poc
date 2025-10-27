package com.example.pix.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoResponseDTO {

    public UUID agendamentoId;
    public String idCorrelacao;
    public String status;
    public String message;
    public OffsetDateTime timestamp;
    private String chavePagador;
    private String nomePagador;
    private String chaveRecebedor;
    private String nomeRecebedor;
    private String moeda;
    private java.math.BigDecimal valor;
    private String periodicidade;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private String motivo;
    private OffsetDateTime criado;
    private OffsetDateTime atualizado;

}