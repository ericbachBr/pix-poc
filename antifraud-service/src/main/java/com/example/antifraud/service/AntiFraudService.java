package com.example.antifraud.service;


import com.example.antifraud.dto.AnaliseFraudeResponse;
import com.example.antifraud.enums.StatusFraude;

import java.util.Map;


public interface AntiFraudService {
    AnaliseFraudeResponse analisar(Map<String, Object> payload);
}


