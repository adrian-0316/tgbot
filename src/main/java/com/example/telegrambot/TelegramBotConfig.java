package com.example.telegrambot;

import com.example.telegrambot.bot.MyTelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

    private final MyTelegramBot myTelegramBot;

    public TelegramBotConfig(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
        System.out.println("TelegramBotConfig: конструктор");
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(myTelegramBot);
        System.out.println("TelegramBot зарегистрирован");
        return botsApi;
    }
}
