package com.gs.lib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Bidi;

public class BidiUtil {

    private static final Logger logger = LoggerFactory.getLogger(BidiUtil.class);

    public static String Reverse_String(String String_IN, Integer convert) {
        if (String_IN == null || convert != 0) {
            return String_IN;
        }

        int Input_Len = String_IN.length();

        Bidi allEnglish = new Bidi(String_IN, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        if (allEnglish.isLeftToRight() || String_IN.length() < 2)
            return String_IN;

        Bidi bidi = new Bidi(String_IN, Bidi.DIRECTION_RIGHT_TO_LEFT);
        int start;
        int end;

        StringBuilder result = new StringBuilder(Input_Len + 1);
        int j = 0;
        int countRun = bidi.getRunCount();
        for (int i = 0; i < countRun; i++) {
            start = bidi.getRunStart(i);
            end = bidi.getRunLimit(i);
            for (; j < end; j++) {
                char A = String_IN.charAt(j);
                if (bidi.getLevelAt(j) % 2 == 1) {
                    result.insert(0, A);
                } else {
                    if (A == '-' && j > 0) {

                        if (String_IN.charAt(j - 1) >= 'א' && String_IN.charAt(j - 1) <= 'ת') {
                            result.insert(0, '-');
                        }
                    } else {
                        if (String_IN.charAt(end - 1) == '-') {
                            if (j < end - 1) {
                                result.insert(0, String_IN.substring(j, end - 1));
                                result.insert(0, String_IN.charAt(end - 1));
                                j = end - 1;
                            } else
                                result.insert(0, A);
                        } else {
                            result.insert(0, String_IN.substring(j, end));
                            j = end - 1;
                        }
                    }
                }
            }
        }

        int index1 = result.indexOf("(");
        int index2 = result.indexOf(")");

        while (index1 != -1 || index2 != -1) {
            if ((index1 < index2 && index1 != -1) || index2 == -1) {
                result.replace(index1, index1 + 1, ")");
                index1 = result.indexOf("(", index1 + 1);
            } else {
                result.replace(index2, index2 + 1, "(");
                index2 = result.indexOf(")", index2 + 1);
            }
        }

        return result.toString();

    }

}
