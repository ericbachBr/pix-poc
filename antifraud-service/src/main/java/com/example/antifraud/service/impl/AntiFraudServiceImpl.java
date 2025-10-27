package com.example.antifraud.service.impl;

import com.example.antifraud.dto.AnaliseFraudeResponse;
import com.example.antifraud.enums.StatusFraude;
import com.example.antifraud.service.AntiFraudService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AntiFraudServiceImpl implements AntiFraudService {

    @Override
    public AnaliseFraudeResponse analisar(Map<String, Object> payload) {
        BigDecimal valor = new BigDecimal(payload.get("valor").toString());
        String chavePagador = payload.get("chavePagador").toString();
        String chaveRecebedor = payload.get("chaveRecebedor").toString();
        String moeda = payload.get("moeda").toString();

        if (valor.compareTo(new BigDecimal("10000")) > 0) {
            return new AnaliseFraudeResponse(StatusFraude.REVISAO_MANUAL,
                    "Transação acima de R$10.000 requer revisão manual");
        }

        if (chavePagador.equals(chaveRecebedor)) {
            return new AnaliseFraudeResponse(StatusFraude.REJEITADO,
                    "Pagador e recebedor não podem ser a mesma chave");
        }

        if (!"BRL".equalsIgnoreCase(moeda)) {
            return new AnaliseFraudeResponse(StatusFraude.REJEITADO,
                    "Somente transações em BRL são permitidas");
        }

        if (chavePagador.length() < 5 || chaveRecebedor.length() < 5) {
            return new AnaliseFraudeResponse(StatusFraude.REJEITADO,
                    "Chaves PIX inválidas (devem conter ao menos 5 caracteres)");
        }

        return new AnaliseFraudeResponse(StatusFraude.APROVADO, "Transação aprovada");
    }
}
