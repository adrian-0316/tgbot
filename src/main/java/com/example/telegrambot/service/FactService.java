package com.example.telegrambot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class FactService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getFact() {
        String url = "https://uselessfacts.jsph.pl/api/v2/facts/random?language=en";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            String body = response.getBody();
            System.out.println("Response body:\n" + body);

            JsonNode jsonNode = objectMapper.readTree(body);

            if (jsonNode.has("text")) {
                return "📚 Факт:\n" + jsonNode.get("text").asText();
            } else {
                return "Не удалось получить факт.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка при получении факта.";
        }
    }
}
