package org.example.telegram;

import org.apache.http.ParseException;
import org.example.information.CurrencyInfo;
import org.example.settings.*;
import org.example.settings.callbacks.*;
import org.example.settings.data.DataStorage;
import org.example.settings.data.User;
import org.example.settings.notification.NotificationMenu;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CurrencyBot extends TelegramLongPollingBot {
    Buttons buttons = new Buttons();
    DataStorage dataStorage = new DataStorage();
    NotificationMenu menu = new NotificationMenu();
    List<String> time = menu.getNotificationTimes();
    UserCurrency userCurrency = new UserCurrency();
    User user = new User();
    BankURL bankURL = new BankURL();
    DecimalPlaces decimalPlaces = new DecimalPlaces();
    CurrencyInfo info = new CurrencyInfo(userCurrency, bankURL, decimalPlaces, this);

    public CurrencyBot() throws MalformedURLException {
        bankURL.setBankURL(new URL("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11"));
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
            String callbackData = update.getCallbackQuery().getData();

            Map<String, CallbackActions> callbackActions = dataStorage.getCallbackActions();
            Map<String, String> bankUrls = dataStorage.getBankUrls();
            Map<String, DecimalPlaces> numberDecimalPlaces = dataStorage.getNumberDecimalPlaces();
            Map<String, UserCurrency> currencies = dataStorage.getCurrencies();

            if (time.contains(callbackData)) {
                if (callbackData.equals("Вимкнути сповіщення")){
                    //turn off notification
                    sendMessage(chatID, "Сповіщення вимкнено", message);
                } else {
                    user.setTime(Integer.parseInt(callbackData));
                    sendMessage(chatID, "Час сповіщень встановлено на " + callbackData, message);
                    //notification realization
                }
            }

            if (update.getCallbackQuery() != null) {
                CallbackActions action = callbackActions.get(callbackData);
                String bankUrl = bankUrls.get(callbackData);
                DecimalPlaces data = numberDecimalPlaces.get(callbackData);
                UserCurrency selectedCurrency = currencies.get(callbackData);

                if (action != null) {
                    action.execute(message, chatID, this, user);
                }

                if (bankUrls.containsKey(callbackData)) {
                    user.setBank(callbackData);
                    try {
                        bankURL.setBankURL(new URL(bankUrl));
                        sendMessage(chatID, "Встановлено " + callbackData, message);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (data != null) {
                    int newDecimalPlaces = Integer.parseInt(callbackData);
                    user.setDecimalPlaces(newDecimalPlaces);
                    decimalPlaces.setDecimalPlaces(newDecimalPlaces);
                    sendMessage(chatID, data.getMessage(), message);
                }

                if (selectedCurrency != null) {
                    user.setCurrency(callbackData);
                    userCurrency.setCurrencyCode(selectedCurrency.getCurrencyCode(), selectedCurrency.getCurrencyName());
                    sendMessage(chatID, "Встановлено " + selectedCurrency.getCurrencyName(), message);
                }
                if (update.getCallbackQuery().getData().equals("get_info")) {
                    try {
                        info.getCurrencyRate(message, chatID);
                    } catch (IOException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
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