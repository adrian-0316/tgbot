package com.example.telegrambot.handler;

import com.example.telegrambot.service.WeatherService;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {

    private final WeatherService weatherService;

    public CommandHandler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public String handle(String messageText) {
        if (messageText == null || messageText.isBlank()) {
            return "Пустое сообщение.";
        }

        String[] parts = messageText.trim().split("\\s+", 2);
        String command = parts[0];
        String arg = parts.length > 1 ? parts[1] : "";

        return switch (command) {
            case "/start" -> "Привет! Я Telegram-бот. Напиши /help, чтобы узнать, что я умею.";
            case "/help" -> """
                    Доступные команды:
                    /start — начать работу с ботом
                    /help — список команд
                    /time — текущее время
                    /weather [город] — погода
                    """;
            case "/time" -> "Текущее время: " + java.time.LocalTime.now().withNano(0);
            case "/weather" -> {
                if (arg.isEmpty()) {
                    yield "Укажи город. Пример: /weather Москва";
                } else {
                    yield weatherService.getWeather(arg);
                }
            }
            default -> "Неизвестная команда. Напиши /help.";
        };
    }
}
