package com.example.antifraud.controller;

import com.example.antifraud.dto.AnaliseFraudeResponse;
import com.example.antifraud.enums.StatusFraude;
import com.example.antifraud.service.AntiFraudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/antifraud")
public class AntiFraudController {

    private final AntiFraudService antiFraudService;

    public AntiFraudController(AntiFraudService antiFraudService) {
        this.antiFraudService = antiFraudService;
    }

    @PostMapping("/check")
    public ResponseEntity<AnaliseFraudeResponse> check(@RequestBody Map<String, Object> payload) {
        AnaliseFraudeResponse resp = antiFraudService.analisar(payload);
        return ResponseEntity.ok(resp);
    }

}