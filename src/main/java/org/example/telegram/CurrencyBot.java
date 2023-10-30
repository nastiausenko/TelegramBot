package org.example.telegram;

import org.example.information.CurrencyInfo;
import org.example.settings.BankURL;
import org.example.settings.Buttons;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.example.settings.callbacks.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


public class CurrencyBot extends TelegramLongPollingBot {
    Buttons buttons = new Buttons();
    Map<String, CallbackActions> callbackActions = new HashMap<>();
    Map<String, String> bankUrls = new HashMap<>();
    Map<String, DecimalPlaces> numberDecimalPlaces = new HashMap<>();
    Map<String, UserCurrency> currencies = new HashMap<>();

    UserCurrency userCurrency = new UserCurrency();
    BankURL bankService = new BankURL();
    DecimalPlaces decimalPlaces = new DecimalPlaces();
    CurrencyInfo info = new CurrencyInfo();

    public CurrencyBot() throws MalformedURLException {
        bankService.setBankURL(new URL("https://api.monobank.ua/bank/currency"));
        callbackActions.put("settings", new SettingsCallback());
        callbackActions.put("decimal_places", new DecimalPlacesCallback());
        callbackActions.put("bank", new BankCallBack());
        callbackActions.put("notification", new NotificationCallback());
        callbackActions.put("currency", new CurrencyCallback());

        bankUrls.put("monobank", "https://api.monobank.ua/bank/currency");
        bankUrls.put("privatbank", "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");
        bankUrls.put("nbu", "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");

        numberDecimalPlaces.put("2", new DecimalPlaces(2, "Встановлено 2 знаки після коми"));
        numberDecimalPlaces.put("3", new DecimalPlaces(3, "Встановлено 3 знаки після коми"));
        numberDecimalPlaces.put("4", new DecimalPlaces(4, "Встановлено 4 знаки після коми"));

        currencies.put("usd", new UserCurrency(840, "USD"));
        currencies.put("eur", new UserCurrency(978, "EUR"));
    }

    @Override
    public String getBotUsername() {
        return Constants.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Long chatID = getChatId(update);
        SendMessage message = new SendMessage();


        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            message.setChatId(chatID);
            buttons.startButtons(message);
            sendApiMethodAsync(message);
        }

        if (update.hasCallbackQuery()) {

            if (update.getCallbackQuery() != null) {
                String callbackData = update.getCallbackQuery().getData();
                CallbackActions action = callbackActions.get(callbackData);
                String bankUrl = bankUrls.get(callbackData);
                DecimalPlaces data = numberDecimalPlaces.get(callbackData);
                UserCurrency selectedCurrency = currencies.get(callbackData);

                if (action != null) {
                    action.execute(message, chatID, this);
                }

                if (bankUrl != null) {
                    try {
                        bankService.setBankURL(new URL(bankUrl));
                        sendMessage(chatID, "Встановлено " + callbackData, message);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (data != null) {
                    int newDecimalPlaces = data.getDecimalPlaces();
                    decimalPlaces.setDecimalPlaces(newDecimalPlaces);
                    sendMessage(chatID, data.getMessage(), message);
                }

                if (selectedCurrency != null) {
                    userCurrency.setCurrencyCode(selectedCurrency.getCurrencyCode(), selectedCurrency.getCurrencyName());
                    sendMessage(chatID, "Встановлено " + selectedCurrency.getCurrencyName(), message);
                }
            }

            if (update.getCallbackQuery().getData().equals("get_info")) {
                try {
                    info.getCurrencyRate(message, userCurrency, bankService, decimalPlaces);
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
                message.setChatId(chatID);
                sendApiMethodAsync(message);
            }
        }
    }

    public Long getChatId(Update update) {
        if (update.hasMessage()){
            return update.getMessage().getChatId();
        }

        if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public void sendMessage(Long chatID, String text, SendMessage message) {
        message.setChatId(chatID);
        message.setText(text);
        sendApiMethodAsync(message);
    }

}