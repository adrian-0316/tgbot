package com.example.telegrambot.model;

import lombok.Data;

@Data
public class WeatherResponse {
    private String cityName;
    private String description;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private double windSpeed;

    public WeatherResponse(String cityName, String description, double temperature, double feelsLike, int humidity, double windSpeed) {
        this.cityName = cityName;
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }
}
