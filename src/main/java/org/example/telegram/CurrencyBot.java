package org.example.telegram;

import org.example.information.CurrencyInfo;
import org.example.settings.BankURL;
import org.example.settings.Buttons;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.example.settings.notification.NotificationMenu;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class CurrencyBot extends TelegramLongPollingBot {
    Buttons buttons = new Buttons();
    UserCurrency userCurrency = new UserCurrency();
    BankURL bankService = new BankURL();
    DecimalPlaces decimalPlaces = new DecimalPlaces();
    CurrencyInfo info = new CurrencyInfo();
    NotificationMenu menu = new NotificationMenu();

    public CurrencyBot() throws MalformedURLException {
        bankService.setBankURL(new URL("https://api.monobank.ua/bank/currency"));
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
            if (update.getCallbackQuery().getData().equals("settings")) {
                message.setChatId(chatID);
                buttons.settingsButtons(message);
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("decimal_places")) {
                message.setChatId(chatID);
                buttons.decimalPlacesButtons(message);
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("bank")) {
                message.setChatId(chatID);
                buttons.bankButtons(message);
                sendApiMethodAsync(message);
            }


            if (update.getCallbackQuery().getData().equals("monobank")) {
                try {
                    bankService.setBankURL(new URL("https://api.monobank.ua/bank/currency"));
                    sendMessage(chatID, "Встановлено Monobank");
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("privatbank")) {
                try {
                    bankService.setBankURL(new URL("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11"));
                    sendMessage(chatID, "Встановлено ПриватБанк");
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (update.getCallbackQuery().getData().equals("nbu")) {
                try {
                    bankService.setBankURL(new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json"));
                    sendMessage(chatID, "Встановлено НБУ");
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals("currency")) {
                message.setChatId(chatID);
                buttons.currencyButtons(message);
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("notification")) {
                message.setChatId(chatID);
                menu.buildTimeMenu(message);
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("usd")) {
                userCurrency.setCurrencyCode(840, "USD");
                sendMessage(chatID, "Встановлено USD");
            }
            if (update.getCallbackQuery().getData().equals("eur")) {
                userCurrency.setCurrencyCode(978, "EUR");
                sendMessage(chatID, "Встановлено EUR");
            }

            if (update.getCallbackQuery().getData().equals("2")) {
                decimalPlaces.setDecimalPlaces(2);
                sendMessage(chatID, "Встановлено 2 знаки після коми");
            }
            if (update.getCallbackQuery().getData().equals("3")) {
                decimalPlaces.setDecimalPlaces(3);
                sendMessage(chatID, "Встановлено 3 знаки після коми");
            }
            if (update.getCallbackQuery().getData().equals("4")) {
                decimalPlaces.setDecimalPlaces(4);
                sendMessage(chatID, "Встановлено 4 знаки після коми");
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

    private void sendMessage(Long chatID, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(text);
        sendApiMethodAsync(message);
    }
}