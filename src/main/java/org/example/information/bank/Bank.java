package org.example.information.bank;

import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.json.JSONArray;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Bank {
    void processCurrencyData(JSONArray currencyData, SendMessage message, UserCurrency userCurrency, DecimalPlaces decimalPlaces);
}
