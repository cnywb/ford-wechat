package com.ford.wechat.hub.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Richard on 16/8/18.
 */
public class DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    protected static Map<String, DateFormat> dateFormatMap = new HashMap();

    public static final String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";
    public static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_DATE_YYYYMMDDHH = "yyyyMMddHH";
    public static final String FORMAT_DATE_YYYYMM = "yyyyMM";
    public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_DATE_SLASH_YYYY_MM_DD = "yyyy/MM/dd";
    public static final String FORMAT_DATE_SLASH_YYYY_M_DD = "yyyy/M/dd";
    public static final String FORMAT_DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_DATETIME_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";
    public static final String FORMAT_DATETIME_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATETIME_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_DATETIME_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_TIME_DEFAULT = "HH:mm:ss";
    public static final String FORMAT_TIME_HH_MM = "HH:mm";
    public static final String FORMAT_TIME_HHMM = "HHmm";
    public static final String FORMAT_TIME_HH_MM_SS = "HH:mm:ss";
    public static final String FORMAT_TIME_HHMMSS = "HHmmss";
    public static final String FORMAT_TIME_HHMMSS_SSS = "HHmmssSSS";

    private DateUtils() {
    }

    protected static DateFormat getCachedDateFormat(String formatPattern) {
        DateFormat dateFormat = (DateFormat)dateFormatMap.get(formatPattern);
        if(dateFormat == null) {
            dateFormat = getDateFormat(formatPattern);
            dateFormatMap.put(formatPattern, getDateFormat(formatPattern));
        }

        return dateFormat;
    }


    public static String formatTime(Date date) {
        log.debug("date:{}", date);
        return formatDate(date, FORMAT_DATETIME_DEFAULT);
    }

    public static String formatDate(Date date, String formatPattern) {
        log.debug("date:{},formatPattern:{}", date, formatPattern);
        return date == null?null:getCachedDateFormat(formatPattern).format(date);
    }


    public static String formatDate(Date date) {
        log.debug("date:{}", date);
        return formatDate(date, FORMAT_DATE_DEFAULT);
    }


    public static String formatDefaultDate(Date date) {
        return date == null?null:getCachedDateFormat("yyyy-MM-dd").format(date);
    }

    public static DateFormat getDateFormat(String formatPattern) {
        return new SimpleDateFormat(formatPattern);
    }

    public static Date parser(String dateStr, String formatPattern) {
        try {
            return getDateFormat(formatPattern).parse(dateStr);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static Date getNextYear(Date date, int yearNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(1, calendar.get(1) + yearNum);
        calendar.set(6, calendar.get(6));
        return calendar.getTime();
    }

    public static Date getNextDateByMonth(Date date, int monthNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(2, calendar.get(2) + monthNum);
        calendar.set(6, calendar.get(6));
        return calendar.getTime();
    }

    public static Date getNextDay(Date date, int dayNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(6, calendar.get(6) + dayNum);
        return calendar.getTime();
    }

    public static long getBetweenDay(Date startDate, Date endDate) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        long days = (d2.getTime().getTime() - d1.getTime().getTime()) / 86400000L;
        return days;
    }

    public static void main(String[] args) {
        Calendar d1 = Calendar.getInstance();
        d1.set(2016, 1, 1);
        Calendar d2 = Calendar.getInstance();
        d2.set(2016, 3, 1);
        System.out.println(getBetweenDay(new Date(), d2.getTime()));
    }
}
