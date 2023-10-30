package org.example.settings;

import lombok.Getter;

@Getter
public class UserCurrency {
    private int currencyCode;
    private String currencyName;

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
}