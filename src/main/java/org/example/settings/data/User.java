package org.example.settings.data;

import lombok.Getter;

@Getter
public class User {
    private int decimalPlaces = 2;
    private String bank = "privatbank";
    private String currency = "usd";
    private int time = 9;

    public void setTime(Integer time) {
        this.time = time;
    }

    public int getTime() {
        return time;
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
