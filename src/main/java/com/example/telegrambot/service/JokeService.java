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
        String url = "https://v2.jokeapi.dev/joke/Any?lang=ru&type=single";

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.has("joke")) {
                return "😄 Шутка:\n" + jsonNode.get("joke").asText();
            } else {
                return "Не удалось получить шутку.";
            }
        } catch (Exception e) {
            return "Ошибка при получении шутки.";
        }
    }
}
