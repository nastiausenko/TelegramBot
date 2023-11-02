package org.example.settings;

import lombok.Getter;

import java.util.List;

@Getter
public class UserCurrency {
    private int currencyCode;
    private String currencyName;
    private List<UserCurrency> currencies;

    public UserCurrency() {
        this.currencyCode = 840;
        this.currencyName = "USD";
    }
    public UserCurrency(int currencyCode, String currencyName) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }
    public void setCurrencyCode(int code, String name) {
        this.currencyCode = code;
        this.currencyName = name;
    }

    public void setCurrencies(List<UserCurrency> currencies) {
        this.currencies = currencies;
    }
}