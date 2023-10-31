package org.example.information.bank;

import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Monobank implements Bank {
    @Override
    public void processCurrencyData(JSONArray currencyData, SendMessage message, UserCurrency userCurrency, DecimalPlaces decimalPlaces) {
        for (int i = 0; i < currencyData.length(); i++) {
            JSONObject currencyObject = currencyData.getJSONObject(i);
            int currencyCodeA = currencyObject.getInt("currencyCodeA");
            int currencyCodeB = currencyObject.getInt("currencyCodeB");
            int chosenCurrencyCode = userCurrency.getCurrencyCode();

            if (currencyCodeA == chosenCurrencyCode && currencyCodeB == 980) {
                String formattedRateBuy = String.format("%." + decimalPlaces.getDecimalPlaces() + "f", currencyObject.getFloat("rateBuy"));
                String formattedRateSell = String.format("%." + decimalPlaces.getDecimalPlaces() + "f", currencyObject.getFloat("rateSell"));

                message.setText("Базова валюта: UAH\n" +
                        "Валюта угоди: " + userCurrency.getCurrencyName() + "\n" +
                        "Дата: " + getFormatDate(currencyObject) + "\n" +
                        "Купівля: " + formattedRateBuy + "\n" +
                        "Продаж: " + formattedRateSell);
            }
        }
    }

    private static String getFormatDate(JSONObject model) {
        long timestamp = model.getLong("date") * 1000L;
        return new SimpleDateFormat("dd MMM yyyy").format(new Date(timestamp));
    }
}
