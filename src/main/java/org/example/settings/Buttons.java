package org.example.settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Buttons {
    private static final String WELCOME_MESSAGE = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";

    public void startButtons(SendMessage message) {

        message.setText(WELCOME_MESSAGE);
      
        attachButtons(message, Map.of(
                "Отримати інфо\n", "get_info",
                "Налаштування\n", "settings"
        ));
    }

    public void settingsButtons(SendMessage message) {
        attachButtons(message, Map.of(
                "Кількість знаків після коми\n", "decimal_places",
                "Банк\n", "bank",
                "Валюти\n", "currency",
                "Час сповіщень\n", "notification"
        ));
    }

    public void decimalPlacesButtons(SendMessage message, int currentDecimalPlaces) {
        attachButtons(message, Map.of(
                (currentDecimalPlaces == 2 ? "2 ✅\n" : "2"), "2",
                (currentDecimalPlaces == 3 ? "3 ✅\n" : "3"), "3",
                (currentDecimalPlaces == 4 ? "4 ✅\n" : "4"), "4"
        ));
    }

    public void bankButtons(SendMessage message) {
        attachButtons(message, Map.of(
                "НБУ\n", "nbu",
                "ПриватБанк\n", "privatbank",
                "Монобанк\n", "monobank"
        ));
    }

    public void currencyButtons(SendMessage message) {
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
