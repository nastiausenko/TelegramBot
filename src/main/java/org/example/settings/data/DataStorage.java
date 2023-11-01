package org.example.settings.data;

import lombok.Getter;
import org.example.settings.DecimalPlaces;
import org.example.settings.UserCurrency;
import org.example.settings.callbacks.*;

import java.util.Map;
import java.util.HashMap;

import static org.example.settings.data.AppConstants.*;

@Getter
public class DataStorage {
    private final Map<String, CallbackActions> callbackActions = new HashMap<>();
    private final Map<String, String> bankUrls = new HashMap<>();
    private final Map<String, DecimalPlaces> numberDecimalPlaces = new HashMap<>();
    private final Map<String, UserCurrency> currencies = new HashMap<>();

    public DataStorage() {
        callbackActions.put("settings", new SettingsCallback());
        callbackActions.put("decimal_places", new DecimalPlacesCallback());
        callbackActions.put("bank", new BankCallBack());
        callbackActions.put("notification", new NotificationCallback());
        callbackActions.put("currency", new CurrencyCallback());

        bankUrls.put("monobank", MONO_URI);
        bankUrls.put("privatbank", PRIVAT_URI);
        bankUrls.put("nbu", NBU_URI);

        numberDecimalPlaces.put("2", new DecimalPlaces(2));
        numberDecimalPlaces.put("3", new DecimalPlaces(3));
        numberDecimalPlaces.put("4", new DecimalPlaces(4));

        currencies.put("usd", new UserCurrency(840, "USD"));
        currencies.put("eur", new UserCurrency(978, "EUR"));
    }
}

