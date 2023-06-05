package tau.ods.gs.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public enum DateFormat {

        YYYYMMDD("yyyyMMdd"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        YYYY_MM_DD_T_HH_MM_SS("yyyy-MM-dd'T'HH:mm:ss"),
        YYYY_MM_DD_T_HH_MM_SS_SSSZ("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        private String format;
        private DateFormat(String format) {
            this.format = format;
        }
    }

    // TODO:
//    private DateTimeFormatter dateTimeFormatter;

    // dateString - 2021-11-15T13:52:58.963Z
    public static Date parse(String dateString, String format) throws ParseException {
        if(! StringUtils.isEmpty(dateString)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(dateString);
        }
        return null;
    }

    public static Date parse(String dateString, DateFormat formatEnum) throws ParseException {
        if(! StringUtils.isEmpty(dateString)) {
            return parse(dateString, formatEnum.format);
        }
        return null;
    }


    public static String format(Date date, String format) {
        if(date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        }
        return null;
    }

    public static String format(Date date, DateFormat formatEnum) {
        if(date != null) {
            return format(date, formatEnum.format);
        }
        return null;
    }

    public static String format(String dateString, DateFormat formatSourceEnum, DateFormat formatTargetEnum) throws ParseException {
        if(! StringUtils.isEmpty(dateString)) {
            return format(parse(dateString, formatSourceEnum), formatTargetEnum);
        }
        return null;
    }
}
