package com.gs.lib.util;

import java.util.Arrays;

public class StringUtils {

    public static boolean isEmpty(String val) {
        return (val == null || val.isEmpty());
    }
    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }

    public static String pad(int padnum, String val) {
        String result = val == null ? "" : String.valueOf(val);
        if (result.length() > padnum) {
            System.out.println("Unexpected padding: length: " + result.length() + " > " + padnum + " result=" + result);
            throw new IllegalStateException("Unexpected padding: length: " + result.length() + " > " + padnum + " result=" + result);
        }
        return padLeft(padnum, ' ', result).toString();
    }

    public static StringBuilder padLeft(int padnum, char chr, String value) {
        StringBuilder sb = new StringBuilder();
        char[] pad = new char[padnum - value.length()];
        Arrays.fill(pad, chr);
        return sb.append(pad).append(value);
    }

}
