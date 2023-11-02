package org.example.information;

import org.apache.http.ParseException;
import org.example.information.bank.Bank;
import org.example.information.bank.Monobank;
import org.example.information.bank.NBU;
import org.example.information.bank.Privatbank;
import org.example.settings.BankURL;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.example.telegram.CurrencyBot;
import org.json.JSONArray;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CurrencyInfo {
    private UserCurrency userCurrency;
    private BankURL bankURL;
    private DecimalPlaces decimalPlaces;
    private CurrencyBot bot;

    public CurrencyInfo(UserCurrency userCurrency, BankURL currencyService, DecimalPlaces decimalPlaces, CurrencyBot bot) {
        this.userCurrency = userCurrency;
        this.bankURL = currencyService;
        this.decimalPlaces = decimalPlaces;
        this.bot = bot;
    }

    public SendDocument getCurrencyRate(SendMessage message, Long chatID)
            throws IOException, ParseException {
        URL url = bankURL.getBankURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        SendDocument document = new SendDocument();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONArray currencyRates = new JSONArray(result.toString());
            Bank bank;

            List<UserCurrency> currencies = userCurrency.getCurrencies();


            if (bankURL.isMonobank()) {
                bank = new Monobank();
            } else if (bankURL.isPrivatbank()) {
                bank = new Privatbank();
            } else {
                bank = new NBU();
            }

            if (currencies == null){
                bank.processCurrencyData(currencyRates, message, userCurrency, decimalPlaces);
                bot.sendMessage(chatID, message.getText(), message);
            } else {
                for (UserCurrency currency : currencies) {
                    bank.processCurrencyData(currencyRates, message, currency, decimalPlaces);
                    bot.sendMessage(chatID, message.getText(), message);
                }
            }
        }
        return document;
    }
}
