package org.example.telegram;

import org.apache.http.ParseException;
import org.example.settings.data.AppConstants;
import org.example.information.CurrencyInfo;
import org.example.settings.*;
import org.example.settings.callbacks.*;
import org.example.settings.data.DataStorage;
import org.example.settings.data.User;
import org.example.settings.notification.Notification;
import org.example.settings.notification.NotificationMenu;
import org.quartz.SchedulerException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyBot extends TelegramLongPollingBot {
    Buttons buttons = new Buttons();
    DataStorage dataStorage = new DataStorage();
    NotificationMenu menu = new NotificationMenu();
    List<String> time = menu.getNotificationTimes();
    UserCurrency userCurrency = new UserCurrency();
    BankURL bankURL = new BankURL();
    DecimalPlaces decimalPlaces = new DecimalPlaces();
    User user = new User();
    List<UserCurrency> selectedCurrencies = new ArrayList<>(2);
    CurrencyInfo info = new CurrencyInfo(userCurrency, bankURL, decimalPlaces, this);
    Notification notification;

    public CurrencyBot() throws MalformedURLException, SchedulerException {
        bankURL.setBankURL(new URL(AppConstants.PRIVAT_URI));
        notification = new Notification(bankURL, decimalPlaces, userCurrency, user);
        notification.start(this);
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
        user.setChadId(chatID);
        SendMessage message = new SendMessage();

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            message.setChatId(chatID);
            buttons.startButtons(message);
            sendApiMethodAsync(message);
        }

        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            int messageIdToDelete = update.getCallbackQuery().getMessage().getMessageId();

            Map<String, CallbackActions> callbackActions = dataStorage.getCallbackActions();
            Map<String, String> bankUrls = dataStorage.getBankUrls();
            Map<String, DecimalPlaces> numberDecimalPlaces = dataStorage.getNumberDecimalPlaces();
            Map<String, UserCurrency> currencies = dataStorage.getCurrencies();

            if (time.contains(callbackData)) {
                deleteMessage(chatID, messageIdToDelete);
                SendMessage newMessage = new SendMessage();
                if (callbackData.equals("Вимкнути сповіщення")){
                    user.setTime(0);
                    menu.buildTimeMenu(newMessage, user);
                } else {
                    int selectedTime = Integer.parseInt(callbackData);
                    user.setTime(selectedTime);
                    menu.buildTimeMenu(newMessage, user);
                }
                newMessage.setChatId(chatID);
                sendApiMethodAsync(newMessage);
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
                    deleteMessage(chatID, messageIdToDelete);
                    SendMessage newMessage = new SendMessage();
                    user.setBank(callbackData);
                    try {
                        bankURL.setBankURL(new URL(bankUrl));
                        buttons.bankButtons(newMessage, user);

                        newMessage.setChatId(chatID);
                        sendApiMethodAsync(newMessage);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (data != null) {
                    deleteMessage(chatID, messageIdToDelete);
                    SendMessage newMessage = new SendMessage();

                    int newDecimalPlaces = Integer.parseInt(callbackData);
                    user.setDecimalPlaces(newDecimalPlaces);
                    decimalPlaces.setDecimalPlaces(newDecimalPlaces);
                    buttons.decimalPlacesButtons(newMessage, user);

                    newMessage.setChatId(chatID);
                    sendApiMethodAsync(newMessage);
                }

                if (selectedCurrency != null) {
                    if (selectedCurrencies.contains(selectedCurrency)){
                        selectedCurrencies.remove(selectedCurrency);
                    } else {
                        selectedCurrencies.add(selectedCurrency);
                    }
                    deleteMessage(chatID, messageIdToDelete);
                    SendMessage newMessage = new SendMessage();

                    user.setCurrency(callbackData);
                    user.setCurrencies(selectedCurrencies);
                    userCurrency.setCurrencies(selectedCurrencies);
                    for (UserCurrency currency : selectedCurrencies) {
                        userCurrency.setCurrencyCode(currency.getCurrencyCode(), currency.getCurrencyName());
                    }
                    buttons.currencyButtons(newMessage, user);
                    SendMessage warningMessage = new SendMessage();

                    if (selectedCurrencies.isEmpty()) {
                        warningMessage.setText("Будь ласка, виберіть валюту");
                    }

                    newMessage.setChatId(chatID);
                    sendApiMethodAsync(newMessage);

                    warningMessage.setChatId(chatID);
                    sendApiMethodAsync(warningMessage);
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

    public void deleteMessage(Long chatID, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatID);
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}