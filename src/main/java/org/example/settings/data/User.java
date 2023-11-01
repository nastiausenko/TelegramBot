package org.example.settings.data;

import lombok.Getter;

@Getter
public class User {
    private int decimalPlaces;
    private String bank;
    private String currency = "usd";
    @Getter
    private int time;

    public User(){
        this.decimalPlaces = 2;
        this.bank = "privatbank";
        this.time = 9;
        this.currency = "usd";
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
}
