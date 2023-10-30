package org.example.information;

import org.apache.http.ParseException;
import org.example.information.bank.Bank;
import org.example.information.bank.Monobank;
import org.example.information.bank.NBU;
import org.example.information.bank.Privatbank;
import org.example.settings.BankURL;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.json.JSONArray;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyInfo {
    public void getCurrencyRate(SendMessage message, UserCurrency userCurrency, BankURL currencyService, DecimalPlaces decimalPlaces)
            throws IOException, ParseException {
        URL url = currencyService.getBankURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONArray currencyRates = new JSONArray(result.toString());
            Bank bank;
            if (currencyService.isMonobank()) {
                bank = new Monobank();
            } else if (currencyService.isPrivatbank()) {
                bank = new Privatbank();
            } else {
                bank = new NBU();
            }

            bank.processCurrencyData(currencyRates, message, userCurrency, decimalPlaces);
        }
    }
}