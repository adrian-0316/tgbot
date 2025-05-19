package com.example.telegrambot.handler;


import com.example.telegrambot.service.FactService;
import com.example.telegrambot.service.JokeService;
import com.example.telegrambot.service.RequestLimiterService;
import com.example.telegrambot.service.WeatherService;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {

    private final WeatherService weatherService;
    private final FactService factService;
    private final JokeService jokeService;
    private final RequestLimiterService requestLimiterService;

    public CommandHandler(WeatherService weatherService, FactService factService, JokeService jokeService, RequestLimiterService requestLimiterService) {
        this.weatherService = weatherService;
        this.factService = factService;
        this.jokeService = jokeService;
        this.requestLimiterService = requestLimiterService;
    }

    public String handle(String messageText, long userId) {
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
                /joke — случайная шутка
                /fact — интересный факт
                """;
            case "/time" -> "Текущее время: " + java.time.LocalTime.now().withNano(0);
            case "/Я_Алиса_Бакуш" -> "Я тебя сильно люблю";
            case "/joke" -> {
                if (requestLimiterService.canRequestJoke(userId)) {
                    requestLimiterService.updateJokeRequestTime(userId);
                    yield jokeService.getJoke();
                } else {
                    yield "😅 Только одна шутка в день! Попробуй завтра.";
                }
            }
            case "/fact" -> {
                if (requestLimiterService.canRequestFact(userId)) {
                    requestLimiterService.updateFactRequestTime(userId);
                    yield factService.getFact();
                } else {
                    yield "📚 Только один факт в день! Попробуй завтра.";
                }
            }
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
