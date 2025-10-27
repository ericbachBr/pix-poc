package com.example.pix.integration;

import com.example.pix.dto.AnaliseFraudeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

public interface AntiFraudClient {
    AnaliseFraudeResponse checkFraud(@RequestBody Map<String, Object> payload);
}
