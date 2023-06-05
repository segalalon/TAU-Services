package com.gs.lib.service;

import com.gs.lib.util.StringUtils;

import java.math.BigInteger;

public class IbanService {

    /*
        An integer contains the following numbers:
            ccc-bbb-00000nnnnnnnn-182100
            (correct only for Israel because IL=1821;
            ccc=bank number, bbb=branch number, nnnnnnnn=account number)

        calculate:
        integer % 97 = result (% = modulo)
        98 – result = check digits

        For example:
        Bank = 10, branch = 838, account number = 02464325
        Integer = 108380000002464325
        Integer % 97 = 87
        98-87=11
        The Iban: IL11.010.X.X0002464325
     */
    public static String calcIBAN_IL(short bank, short snif, long chn) {
        final String IL = "IL";
        final String IL_CODE = "182100";
        final char chr = '0';
        StringBuilder ibanStr = new StringBuilder();
        ibanStr.append(StringUtils.padLeft(3, chr, String.valueOf(bank)))
                .append(StringUtils.padLeft(3, chr, String.valueOf(snif)))
                .append(StringUtils.padLeft(13, chr, String.valueOf(chn)))
                .append(IL_CODE); // correct only for Israel because IL=1821

        BigInteger intVal = new BigInteger(ibanStr.toString(), 10);
        BigInteger mod = intVal.mod(BigInteger.valueOf(97));
        BigInteger chkDigits = BigInteger.valueOf(98).subtract(mod);
        return IL + chkDigits + ibanStr.substring(0, ibanStr.length() - IL_CODE.length());
    }

    public static void main(String[] args) {
        String iban = calcIBAN_IL((short)10, (short)838, 2464325);
        System.out.println(iban);
        System.out.println(iban.equals("IL110108380000002464325"));
    }
}
