package org.example.settings;

public class UserCurrency {
    private int currencyCode;
    private String currencyName;

    public UserCurrency() {
        this.currencyCode = 840;
        this.currencyName = "USD";
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyCode(int code, String name) {
        this.currencyCode = code;
        this.currencyName = name;
    }
}