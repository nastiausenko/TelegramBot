package org.example.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class MonobankModel {
    Integer currencyCodeA;
    Integer currencyCodeB;
    Date date;
    Float rateSell;
    Float rateBuy;
}

