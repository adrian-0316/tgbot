package com.example.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().trim();
            long chatId = update.getMessage().getChatId();

            String replyText;

            switch (messageText) {
                case "/start" -> replyText = "Привет! Я Telegram-бот. Напиши /help, чтобы узнать, что я умею.";
                case "/help" -> replyText = """
                Доступные команды:
                /start — начать работу с ботом
                /help — список команд
                /time — текущее время
                """;
                case "/time" -> replyText = "Текущее время: " + java.time.LocalTime.now().withNano(0);
                default -> replyText = "Неизвестная команда. Напиши /help.";
            }

            SendMessage message = new SendMessage(String.valueOf(chatId), replyText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
