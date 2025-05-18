package com.example.telegrambot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JokeService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getJoke() {
        String url = "https://v2.jokeapi.dev/joke/Any?lang=en";

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.has("error") && jsonNode.get("error").asBoolean()) {
                return "Не удалось получить шутку.";
            }

            if ("single".equals(jsonNode.get("type").asText())) {
                return "😄 Шутка:\n" + jsonNode.get("joke").asText();
            } else if ("twopart".equals(jsonNode.get("type").asText())) {
                return "😄 Шутка:\n" + jsonNode.get("setup").asText() + "\n" + jsonNode.get("delivery").asText();
            } else {
                return "Неизвестный формат шутки.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при получении шутки.";
        }
    }
}
