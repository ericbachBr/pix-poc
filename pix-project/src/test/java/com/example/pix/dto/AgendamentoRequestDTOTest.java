
package com.example.pix.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarDTOComDadosCorretos() {
        AgendamentoRequestDTO dto = new AgendamentoRequestDTO();
        dto.setValor(new BigDecimal("100.00"));
        dto.setMoeda("BRL");
        dto.setChavePagador("pagador-123");
        dto.setNomePagador("Maria");
        dto.setChaveRecebedor("recebedor-456");
        dto.setNomeRecebedor("Loja");
        dto.setPeriodicidade("UNICA");
        dto.setDataInicio(OffsetDateTime.now());
        dto.setDataFim(OffsetDateTime.now().plusDays(30));

        Set<ConstraintViolation<AgendamentoRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deve haver violações de validação");
    }

    @Test
    void deveDetectarCamposObrigatoriosNulosOuEmBranco() {
        AgendamentoRequestDTO dto = new AgendamentoRequestDTO();

        Set<ConstraintViolation<AgendamentoRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deve haver violações de validação");
    }

    @Test
    void deveDetectarValorMenorQuePermitido() {
        AgendamentoRequestDTO dto = new AgendamentoRequestDTO();
        dto.setValor(new BigDecimal("0.00"));
        dto.setMoeda("BRL");
        dto.setChavePagador("pagador-123");
        dto.setNomePagador("Maria");
        dto.setChaveRecebedor("recebedor-456");
        dto.setNomeRecebedor("Loja");
        dto.setPeriodicidade("UNICA");
        dto.setDataInicio(OffsetDateTime.now());

        Set<ConstraintViolation<AgendamentoRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deve haver violação para valor menor que 0.01");
    }

    @Test
    void deveDetectarCamposEmBranco() {
        AgendamentoRequestDTO dto = new AgendamentoRequestDTO();
        dto.setValor(new BigDecimal("10.00"));
        dto.setMoeda("");
        dto.setChavePagador(" ");
        dto.setNomePagador("");
        dto.setChaveRecebedor("");
        dto.setNomeRecebedor("");
        dto.setPeriodicidade("");
        dto.setDataInicio(OffsetDateTime.now());

        Set<ConstraintViolation<AgendamentoRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deve haver violação para campos em branco");
    }
}
