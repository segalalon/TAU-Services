package com.gs.lib.model;

public class CurrencyExchangeInfo {
    String kod_matbea_from; // 3 letters
    String kod_matbea_to;  //3
    String exchangeRate; // 4
    String sourceCurrency; //3
    String targetCurrency; //3
    String unitCurrency; //3
    String quotationDate; //9

    public CurrencyExchangeInfo(String kod_matbea_from, String kod_matbea_to, String exchangeRate, String sourceCurrency, String targetCurrency, String unitCurrency, String quotationDate) {
        this.kod_matbea_from = kod_matbea_from;
        this.kod_matbea_to = kod_matbea_to;
        this.exchangeRate = exchangeRate;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.unitCurrency = unitCurrency;
        this.quotationDate = quotationDate;
    }

    @Override
    public String toString() {
        return
                kod_matbea_from +
                kod_matbea_to +
                exchangeRate +
                sourceCurrency +
                targetCurrency +
                unitCurrency +
                quotationDate;
    }
}
