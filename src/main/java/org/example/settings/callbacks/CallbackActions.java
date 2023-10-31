package org.example.settings.callbacks;

import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface CallbackActions {
    void execute(SendMessage message, Long chatID, CurrencyBot bot);
}
