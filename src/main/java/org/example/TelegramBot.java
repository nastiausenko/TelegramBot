package org.example;

import org.example.telegram.TelegramBotService;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot {
    public static void main(String[] args) throws TelegramApiException {
        new TelegramBotService();
    }
}
