package com.example.pix.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoRequestDTO {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valor;

    @NotBlank
    private String moeda;

    @NotBlank
    private String chavePagador;

    @NotBlank
    private String nomePagador;

    @NotBlank
    private String chaveRecebedor;

    @NotBlank
    private String nomeRecebedor;

    @NotBlank
    private String periodicidade;

    @NotNull
    private OffsetDateTime dataInicio;

    private OffsetDateTime dataFim;
}