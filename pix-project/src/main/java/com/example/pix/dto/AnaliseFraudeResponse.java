package com.example.pix.dto;

import com.example.pix.enums.StatusFraude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnaliseFraudeResponse {
    private String status;
    private String motivo;
}
