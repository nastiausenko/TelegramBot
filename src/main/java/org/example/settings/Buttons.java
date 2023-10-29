package org.example.settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Buttons {
    private static final String WELCOME_MESSAGE = "Ласкаво просимо. Цей бот допопможе відслідковувати актуальні курси валют";
    private static final String SETTINGS = "Налаштування";
    private static final String DECIMAL_PLACES_MESSAGE = "Виберіть кількість знаків після коми";
    private static final String BANK_MESSAGE = "Виберіть банк";
    private static final String CURRENCY_MESSAGE = "Виберіть валюту";



    public void startButtons(SendMessage message) {
        message.setText(WELCOME_MESSAGE);
        attachButtons(message, Map.of(
                "Отримати інфо\n", "get_info",
                "Налаштування\n", "settings"
        ));
    }

    public void settingsButtons(SendMessage message) {
        message.setText(SETTINGS);
        attachButtons(message, Map.of(
                "Кількість знаків після коми\n", "decimal_places",
                "Банк\n", "bank",
                "Валюти\n", "currency",
                "Час сповіщень\n", "notification"
        ));
    }

    public void decimalPlacesButtons(SendMessage message) {
        message.setText(DECIMAL_PLACES_MESSAGE);
        attachButtons(message, Map.of(
                "2\n", "2",
                "3\n", "3",
                "4\n", "4"
        ));
    }

    public void bankButtons(SendMessage message) {
        message.setText(BANK_MESSAGE);
        attachButtons(message, Map.of(
                "НБУ\n", "nbu",
                "ПриватБанк\n", "privatbank",
                "Монобанк\n", "monobank"
        ));
    }

    public void currencyButtons(SendMessage message) {
        message.setText(CURRENCY_MESSAGE);
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
