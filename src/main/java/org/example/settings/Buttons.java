package org.example.settings;

import org.example.settings.data.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class Buttons {

    private static final String WELCOME_MESSAGE = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";
    List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

    public void startButtons(SendMessage message) {

        message.setText(WELCOME_MESSAGE);

        buttons.add(List.of(createButton("Отримати інфо", "get_info",false)));
        buttons.add(List.of(createButton("Налаштування", "settings", false)));

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(buttons).build();

        message.setReplyMarkup(markup);
    }

    public void settingsButtons(SendMessage message) {

        buttons.add(List.of(createButton("Кількість знаків після коми", "decimal_places",false)));
        buttons.add(List.of(createButton("Банк", "bank", false)));
        buttons.add(List.of(createButton("Валюти", "currency", false)));
        buttons.add(List.of(createButton("Час сповіщень", "notification", false)));


        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(buttons).build();

        message.setReplyMarkup(markup);

    }

    public void decimalPlacesButtons(SendMessage message, User user) {
        buttons.add(List.of(createButton("2", "2",
                "2".equals(Integer.toString(user.getDecimalPlaces())))));
        buttons.add(List.of(createButton("3", "3",
                "3".equals(Integer.toString(user.getDecimalPlaces())))));
        buttons.add(List.of(createButton("4", "4",
                "4".equals(Integer.toString(user.getDecimalPlaces())))));


        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(buttons).build();

        message.setText("Виберіть банк:");
        message.setReplyMarkup(markup);

    }

    public void bankButtons(SendMessage message, User user) {
        buttons.add(List.of(createButton("НБУ", "nbu", "nbu".equals(user.getBank()))));
        buttons.add(List.of(createButton("ПриватБанк", "privatbank", "privatbank".equals(user.getBank()))));
        buttons.add(List.of(createButton("Монобанк", "monobank", "monobank".equals(user.getBank()))));


        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(buttons).build();

        message.setText("Виберіть банк:");
        message.setReplyMarkup(markup);

    }

    public void currencyButtons(SendMessage message, User user) {
        buttons.add(List.of(createButton("EUR", "eur", "eur".equals(user.getCurrency()))));
        buttons.add(List.of(createButton("USD", "usd", "usd".equals(user.getCurrency()))));

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(buttons).build();
        message.setReplyMarkup(markup);

    }

    private InlineKeyboardButton createButton(String text, String callbackData, boolean checked) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setCallbackData(callbackData);

        if (checked) {
            button.setText("✅ " + text);
        } else {
            button.setText(text);
        }

        return button;
    }
}
