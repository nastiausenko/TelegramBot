package org.example.settings.data;

import lombok.Getter;
import org.example.information.CurrencyInfo;


@Getter
public class User {
    private int decimalPlaces;
    private String bank;
    private String currency;

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
