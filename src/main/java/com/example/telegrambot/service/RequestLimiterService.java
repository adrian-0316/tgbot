package com.example.telegrambot.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestLimiterService {

    private final Map<Long, LocalDate> lastJokeRequest = new HashMap<>();
    private final Map<Long, LocalDate> lastFactRequest = new HashMap<>();

    public boolean canRequestJoke(long userId) {
        LocalDate today = LocalDate.now();
        return lastJokeRequest.getOrDefault(userId, LocalDate.MIN).isBefore(today);
    }

    public boolean canRequestFact(long userId) {
        LocalDate today = LocalDate.now();
        return lastFactRequest.getOrDefault(userId, LocalDate.MIN).isBefore(today);
    }

    public void updateJokeRequestTime(long userId) {
        lastJokeRequest.put(userId, LocalDate.now());
    }

    public void updateFactRequestTime(long userId) {
        lastFactRequest.put(userId, LocalDate.now());
    }
}
