package org.example.settings;

import lombok.Getter;


@Getter
public class DecimalPlaces {
    private int decimalPlaces;

    public DecimalPlaces() {
        this.decimalPlaces = 2;
    }

    public DecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
}