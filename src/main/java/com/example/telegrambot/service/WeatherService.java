package com.example.telegrambot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getWeather(String city) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&lang=ru&appid=%s",
                city, apiKey
        );

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            // Парсим нужные поля из JSON
            String cityName = root.path("name").asText();
            JsonNode main = root.path("main");
            double temp = main.path("temp").asDouble();
            double feelsLike = main.path("feels_like").asDouble();
            int humidity = main.path("humidity").asInt();

            String weatherDescription = root.path("weather").get(0).path("description").asText();

            return String.format(
                    "Погода в %s:\n%s\nТемпература: %.1f°C\nОщущается как: %.1f°C\nВлажность: %d%%",
                    cityName, weatherDescription, temp, feelsLike, humidity
            );
        } catch (Exception e) {
            return "Ошибка при получении погоды для " + city;
        }
    }
}