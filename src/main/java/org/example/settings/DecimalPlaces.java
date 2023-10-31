package org.example.settings;

public class DecimalPlaces {
    private int decimalPlaces;
    private String message;

    public DecimalPlaces() {
        this.decimalPlaces = 2;
    }

    public DecimalPlaces(int decimalPlaces, String message) {
        this.decimalPlaces = decimalPlaces;
        this.message = message;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public String getMessage(){
        return message;
    }


}