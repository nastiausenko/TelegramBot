package org.example.settings.callbacks;

import org.example.settings.data.User;
import org.example.settings.notification.NotificationMenu;
import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class NotificationCallback implements CallbackActions {
    @Override
    public void execute(SendMessage message, Long chatID, CurrencyBot bot, User user) {
        message.setChatId(chatID);
        NotificationMenu menu = new NotificationMenu();
        menu.buildTimeMenu(message, user);
        bot.sendMessage(chatID, "Виберіть час сповіщень", message);
    }
}
