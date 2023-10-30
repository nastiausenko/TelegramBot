package org.example.settings.callbacks;

import org.example.settings.Buttons;
import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CurrencyCallback implements CallbackActions {
    @Override
    public void execute(SendMessage message, Long chatID, CurrencyBot bot) {
        message.setChatId(chatID);
        Buttons buttons = new Buttons();
        buttons.currencyButtons(message);
        bot.sendMessage(chatID, "Виберіть валюту", message);
    }
}
