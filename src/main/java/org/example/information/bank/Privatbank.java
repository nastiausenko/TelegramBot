package org.example.information.bank;

import org.example.information.model.PrivatBankModel;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Privatbank implements Bank {

    @Override
    public void processCurrencyData(JSONArray currencyData, SendMessage message, UserCurrency userCurrency, DecimalPlaces decimalPlaces) {
        for (int i = 0; i < currencyData.length(); i++) {
            JSONObject currencyObject = currencyData.getJSONObject(i);
            String ccy = currencyObject.getString("ccy");
            String base_ccy = currencyObject.getString("base_ccy");
            String chosenCurrency = userCurrency.getCurrencyName();

            if (ccy.equals(chosenCurrency)) {
                PrivatBankModel privatBankModel = new PrivatBankModel();
                privatBankModel.setCcy(ccy);
                privatBankModel.setBase_ccy(base_ccy);
                privatBankModel.setBuy(currencyObject.getFloat("buy"));
                privatBankModel.setSale(currencyObject.getFloat("sale"));

                String formattedRateBuy = String.format("%." + decimalPlaces.getDecimalPlaces() + "f", privatBankModel.getBuy());
                String formattedRateSell = String.format("%." + decimalPlaces.getDecimalPlaces() + "f", privatBankModel.getSale());

                message.setText("Базова валюта: UAH\n" +
                        "Валюта угоди: " + userCurrency.getCurrencyName() + "\n" +
                        "Купівля: " + formattedRateBuy + "\n" +
                        "Продаж: " + formattedRateSell);
            }
        }
    }
}
