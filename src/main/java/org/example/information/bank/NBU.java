package org.example.information.bank;

//import org.example.information.model.NBUModel;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NBU implements Bank {

    @Override
    public void processCurrencyData(JSONArray currencyData, SendMessage message, UserCurrency userCurrency, DecimalPlaces decimalPlaces) {
        for (int i = 0; i < currencyData.length(); i++) {
            JSONObject currencyObject = currencyData.getJSONObject(i);
            String cc = currencyObject.getString("cc");
            String chosenCurrency = userCurrency.getCurrencyName();

            if (cc.equals(chosenCurrency)) {
//                NBUModel nbuModel = new NBUModel();
//                nbuModel.setCc(cc);
//                nbuModel.setRate(currencyObject.getFloat("rate"));
//                nbuModel.setExchangedate(parseNBUDate(currencyObject.getString("exchangedate")));

                String formattedRate = String.format("%." + decimalPlaces.getDecimalPlaces() + "f",
                        currencyObject.getFloat("rate"));

                message.setText("Базова валюта: UAH\n" +
                        "Валюта угоди: " + userCurrency.getCurrencyName() + "\n" +
                        "Дата: " + getFormatDate(parseNBUDate(currencyObject.getString("exchangedate"))) + "\n" +
                        "Курс: " + formattedRate);
            }
        }
    }
    private static Date parseNBUDate(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            return format.parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getFormatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        return format.format(date);
    }
}
