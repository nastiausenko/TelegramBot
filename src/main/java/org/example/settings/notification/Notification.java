package org.example.settings.notification;

import org.apache.http.ParseException;
import org.example.information.CurrencyInfo;
import org.example.settings.BankURL;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.example.settings.data.User;
import org.example.telegram.CurrencyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Notification {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final BankURL bankURL;
    private final DecimalPlaces decimalPlaces;
    private final UserCurrency currency;
    private final User user;

    public Notification(BankURL bankURL, DecimalPlaces decimalPlaces, UserCurrency currency, User user) {
        this.bankURL = bankURL;
        this.decimalPlaces = decimalPlaces;
        this.currency = currency;
        this.user = user;
    }

    public void start(CurrencyBot bot) {
        final Runnable checker = () -> {
            LocalTime time = LocalTime.now();
            int now = time.getHour();

            if (now == user.getTime()) {
                Long chatId = user.getChadId();
                SendMessage message = new SendMessage();
                message.setChatId(chatId);

                try {
                    CurrencyInfo currencyInfo = new CurrencyInfo(currency, bankURL, decimalPlaces, bot);
                    bot.execute(currencyInfo.getCurrencyRate(message, chatId));
                } catch (TelegramApiException | IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        scheduler.scheduleAtFixedRate(checker, 0, 1, TimeUnit.SECONDS);
    }
}
