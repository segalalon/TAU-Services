package com.gs.lib.service;

import com.gs.lib.model.CurrencyExchangeInfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.gs.lib.util.StringUtils.pad;
import static java.lang.String.valueOf;

public class FieldGenerator {

    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public static String generateTransactionId(Date taIbud, int missiduri ) {
        return dateFormat.format(taIbud) + missiduri;
    }

    public static CurrencyExchangeInfo generateCurrencyExchangeInfo(
            String matbeaSource, String matbeaTarget, BigDecimal exchangeRate, short sourceCurrency, short targetCurrency, short unitCurrency, Date quotationDate) {

        // Fixing the index out of bound exception
        String exchangeRateStr = "";

        if (exchangeRate != null) {
            if (exchangeRate.toPlainString().length() < 3) {
                exchangeRateStr = exchangeRate.toPlainString();
            } else {
                exchangeRateStr = exchangeRate.toPlainString().substring(0, 3);
            }
        }


        return new CurrencyExchangeInfo(pad(3, matbeaSource),
                pad(3, matbeaTarget),
                //pad(4, exchangeRate.toPlainString().substring(0,3)),
                pad(4, exchangeRateStr),
                pad(3, valueOf(sourceCurrency)),
                pad(3, valueOf(targetCurrency)),
                pad(3, valueOf(unitCurrency)),
                pad(8, dateFormat.format(quotationDate)));
    }
}
