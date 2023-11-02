package org.example.settings;

import org.example.settings.data.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class Buttons {

    private static final String WELCOME_MESSAGE = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";
    private List<List<InlineKeyboardButton>> settingsButtons = new ArrayList<>();
    private List<List<InlineKeyboardButton>> startButtons = new ArrayList<>();
    private List<List<InlineKeyboardButton>> decimalButtons = new ArrayList<>();
    private List<List<InlineKeyboardButton>> bankButtons = new ArrayList<>();
    private List<List<InlineKeyboardButton>> currencyButtons = new ArrayList<>();



    public void startButtons(SendMessage message) {
        startButtons.clear();
        message.setText(WELCOME_MESSAGE);

        startButtons.add(List.of(createButton("Отримати інфо", "get_info",false)));
        startButtons.add(List.of(createButton("Налаштування", "settings", false)));

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(startButtons).build();

        message.setReplyMarkup(markup);
    }

    public void settingsButtons(SendMessage message) {
        settingsButtons.clear();
        settingsButtons.add(List.of(createButton("Кількість знаків після коми", "decimal_places",false)));
        settingsButtons.add(List.of(createButton("Банк", "bank", false)));
        settingsButtons.add(List.of(createButton("Валюти", "currency", false)));
        settingsButtons.add(List.of(createButton("Час сповіщень", "notification", false)));

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(settingsButtons).build();
        message.setReplyMarkup(markup);
    }

    public void decimalPlacesButtons(SendMessage message, User user) {
        decimalButtons.clear();
        decimalButtons.add(List.of(createButton("2", "2",
                "2".equals(Integer.toString(user.getDecimalPlaces())))));
        decimalButtons.add(List.of(createButton("3", "3",
                "3".equals(Integer.toString(user.getDecimalPlaces())))));
        decimalButtons.add(List.of(createButton("4", "4",
                "4".equals(Integer.toString(user.getDecimalPlaces())))));


        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(decimalButtons).build();

        message.setText("Виберіть кількість знаків після коми:");
        message.setReplyMarkup(markup);
    }

    public void bankButtons(SendMessage message, User user) {
        bankButtons.clear();
        bankButtons.add(List.of(createButton("НБУ", "nbu", "nbu".equals(user.getBank()))));
        bankButtons.add(List.of(createButton("ПриватБанк", "privatbank", "privatbank".equals(user.getBank()))));
        bankButtons.add(List.of(createButton("Монобанк", "monobank", "monobank".equals(user.getBank()))));


        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(bankButtons).build();

        message.setText("Виберіть банк");
        message.setReplyMarkup(markup);
    }

    public void currencyButtons(SendMessage message, User user) {
        currencyButtons.clear();
        List<UserCurrency> selectedCurrencies = user.getCurrencies();
        List<String> allCurrencyNames = new ArrayList<>();
        allCurrencyNames.add("USD");
        allCurrencyNames.add("EUR");

        for (String currencyName : allCurrencyNames) {
            boolean checked = selectedCurrencies.stream()
                    .anyMatch(currency -> currency.getCurrencyName().equals(currencyName));

            currencyButtons.add(List.of(createButton(currencyName, currencyName.toLowerCase(), checked)));
        }

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(currencyButtons).build();
        message.setText("Виберіть валюту:");
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
