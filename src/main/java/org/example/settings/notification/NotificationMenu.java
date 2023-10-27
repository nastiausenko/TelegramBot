package org.example.settings.notification;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class NotificationMenu {
    public void buildTimeMenu(SendMessage message) {
        message.setText("Виберіть час сповіщень");
        List<KeyboardRow> keyboard = getKeyboardRows();

        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();

        message.setReplyMarkup(replyKeyboardMarkup);
    }

    private static List<KeyboardRow> getKeyboardRows() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();

        row1.add("9.00");
        row1.add("10.00");
        row1.add("11.00");

        row2.add("12.00");
        row2.add("13.00");
        row2.add("14.00");

        row3.add("15.00");
        row3.add("16.00");
        row3.add("17.00");

        row4.add("18.00");
        row4.add("Вимкнути сповіщення");

        return List.of(row1, row2, row3, row4);
    }
}
