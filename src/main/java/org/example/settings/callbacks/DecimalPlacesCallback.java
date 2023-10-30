package org.example.settings.callbacks;

import org.example.settings.Buttons;
import org.example.settings.DecimalPlaces;
import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class DecimalPlacesCallback implements CallbackActions {
    @Override
    public void execute(SendMessage message, Long chatID, CurrencyBot bot) {
        message.setChatId(chatID);
        Buttons buttons = new Buttons();
        DecimalPlaces decimalPlaces = new DecimalPlaces();
        buttons.decimalPlacesButtons(message, decimalPlaces.getDecimalPlaces());
        bot.sendMessage(chatID, "Виберіть кількість знаків після коми", message);
    }
}
