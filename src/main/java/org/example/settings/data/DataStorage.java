package org.example.settings.data;

import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.example.settings.callbacks.*;

import java.util.Map;
import java.util.HashMap;

public class DataStorage {
    private Map<String, CallbackActions> callbackActions = new HashMap<>();
    private Map<String, String> bankUrls = new HashMap<>();
    private Map<String, DecimalPlaces> numberDecimalPlaces = new HashMap<>();
    private Map<String, UserCurrency> currencies = new HashMap<>();

    public DataStorage() {
        // Ініціалізуйте ваші мапи тут
        callbackActions.put("settings", new SettingsCallback());
        callbackActions.put("decimal_places", new DecimalPlacesCallback());
        callbackActions.put("bank", new BankCallBack());
        callbackActions.put("notification", new NotificationCallback());
        callbackActions.put("currency", new CurrencyCallback());

        bankUrls.put("monobank", "https://api.monobank.ua/bank/currency");
        bankUrls.put("privatbank", "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");
        bankUrls.put("nbu", "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");

        numberDecimalPlaces.put("2", new DecimalPlaces(2, "Встановлено 2 знаки після коми"));
        numberDecimalPlaces.put("3", new DecimalPlaces(3, "Встановлено 3 знаки після коми"));
        numberDecimalPlaces.put("4", new DecimalPlaces(4, "Встановлено 4 знаки після коми"));

        currencies.put("usd", new UserCurrency(840, "USD"));
        currencies.put("eur", new UserCurrency(978, "EUR"));
    }

    public Map<String, CallbackActions> getCallbackActions() {
        return callbackActions;
    }

    public Map<String, String> getBankUrls() {
        return bankUrls;
    }

    public Map<String, DecimalPlaces> getNumberDecimalPlaces() {
        return numberDecimalPlaces;
    }

    public Map<String, UserCurrency> getCurrencies() {
        return currencies;
    }
}

