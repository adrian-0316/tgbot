package com.example.telegrambot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FactService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getFact() {
        String url = "https://uselessfacts.jsph.pl/random.json?language=ru";

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.has("text")) {
                return "📚 Факт:\n" + jsonNode.get("text").asText();
            } else {
                return "Не удалось получить факт.";
            }
        } catch (Exception e) {
            return "Ошибка при получении факта.";
        }
    }
}
