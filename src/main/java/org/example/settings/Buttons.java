package org.example.settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Buttons {

    public void startButtons(SendMessage message) {
        message.setText("Ласкаво просимо. Цей бот допопможе відслідковувати актуальні курси валют");
        attachButtons(message, Map.of(
                "Отримати інфо\n", "get_info",
                "Налаштування\n", "settings"
        ));
    }

    public void settingsButtons(SendMessage message) {
        message.setText("Налаштування");
        attachButtons(message, Map.of(
                "Кількість знаків після коми\n", "decimal_places",
                "Банк\n", "bank",
                "Валюти\n", "currency",
                "Час сповіщень\n", "notification"
        ));
    }

    public void decimalPlacesButtons(SendMessage message) {
        message.setText("Виберіть кількість знаків після коми");
        attachButtons(message, Map.of(
                "2\n", "2",
                "3\n", "3",
                "4\n", "4"
        ));
    }

    public void bankButtons(SendMessage message) {
        message.setText("Виберіть банк");
        attachButtons(message, Map.of(
                "НБУ\n", "nbu",
                "ПриватБанк\n", "privatbank",
                "Монобанк\n", "monobank"
        ));
    }

    public void currencyButtons(SendMessage message) {
        message.setText("Виберіть валюту");
        attachButtons(message, Map.of(
                "EUR\n", "eur",
                "USD\n", "usd"
        ));
    }

    private void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String buttonName: buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);
            keyboard.add(List.of(button));
        }

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
}
