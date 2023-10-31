package org.example.settings.callbacks;

import org.example.settings.Buttons;
import org.example.settings.data.User;
import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class BankCallBack implements CallbackActions {
    @Override
    public void execute(SendMessage message, Long chatID, CurrencyBot bot, User user) {
        message.setChatId(chatID);
        Buttons buttons = new Buttons();
        buttons.bankButtons(message, user);
        bot.sendMessage(chatID, "Виберіть банк", message);
    }
}