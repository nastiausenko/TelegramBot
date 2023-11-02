package org.example.settings.data;

import lombok.Getter;
import org.example.settings.UserCurrency;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {
    private int decimalPlaces;
    private String bank;
    private String currency;
    @Getter
    private int time;
    @Getter
    private List<UserCurrency> currencies;

    public User(){
        this.decimalPlaces = 2;
        this.bank = "privatbank";
        this.time = 9;
        this.currency = "usd";
        this.currencies = new ArrayList<>();
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCurrencies(List<UserCurrency> currencies) {
        this.currencies = currencies;
    }
}
