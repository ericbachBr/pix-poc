package com.example.antifraud.dto;

import com.example.antifraud.enums.StatusFraude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnaliseFraudeResponse {
    private StatusFraude status;
    private String motivo;
}
