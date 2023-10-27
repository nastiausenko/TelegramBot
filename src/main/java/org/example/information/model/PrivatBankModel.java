package org.example.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class PrivatBankModel {
    String ccy;
    String base_ccy;
    Float buy;
    Float sale;
    Date date;
}
