package org.example.settings.notification;

import lombok.Getter;
import org.example.settings.data.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class NotificationMenu {

    private final List<String> notificationTimes = Arrays.asList(
            "9", "10", "11",
            "12", "13", "14",
            "15", "16", "17",
            "23", "Вимкнути сповіщення");

    public void buildTimeMenu(SendMessage message, User user) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        for (String time : notificationTimes) {
            buttons.add(createTimeButton(time, user.getTime()));
        }

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i += 3) {
            keyboard.add(buttons.subList(i, Math.min(i + 3, buttons.size())));
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);

        message.setText("Виберіть час сповіщень");
        message.setReplyMarkup(markup);
    }

    private InlineKeyboardButton createTimeButton(String time, int selectedTime) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        if (time.equals("Вимкнути сповіщення") && selectedTime == 0) {
            button.setText("✅ " + time);
        } else {
            button.setText(time + (time.equals(Integer.toString(selectedTime)) ? " ✅" : ""));
        }
        button.setCallbackData(time);
        return button;
    }
}
