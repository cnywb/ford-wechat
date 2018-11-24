package com.ford.wechat.date;/**
 * Created by jovi on 03/12/2016.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2016-12-03 11:36
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class DateUtils {
    public static String FORMAT_DATE_TIME_DEFAULT="yyyy-MM-dd HH:mm:ss";

    public static String FORMAT_DATE_DEFAULT="yyyy-MM-dd";

    public static String FORMAT_DATE_TIME_YYYYMMDDHHMMSS="yyyyMMddHHmmss";

    public static String FORMAT_DATE_YYYYMMDD="yyyyMMdd";

    public static Date parseDate(String text,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateNo(){
        Date now = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DATE_YYYYMMDD);
        return sdf.format(now);
    }

    public static String formatDate(Long time){
        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DATE_TIME_DEFAULT);
        return sdf.format(time);
    }

    public static Date formatEndDate(Date endDate){
        if(endDate==null){
            return null;
        }
        String endDateStr = io.dabing.common.util.DateUtils.format(endDate, io.dabing.common.util.DateUtils.FORMAT_DATE_DEFAULT);
        endDateStr+=" 23:59:59";
        return parseDate(endDateStr, io.dabing.common.util.DateUtils.FORMAT_DATE_TIME_DEFAULT);
    }

    public static Date formatStartDate(Date startDate){
        if(startDate==null){
            return null;
        }
        String endDateStr = io.dabing.common.util.DateUtils.format(startDate, io.dabing.common.util.DateUtils.FORMAT_DATE_DEFAULT);
        endDateStr+=" 00:00:00";
        return parseDate(endDateStr, io.dabing.common.util.DateUtils.FORMAT_DATE_TIME_DEFAULT);
    }

    public static Date formatEndDate(String endDateStr){
        endDateStr+=" 23:59:59";
        return parseDate(endDateStr, io.dabing.common.util.DateUtils.FORMAT_DATE_TIME_YYYYMMdd);
    }

    public static Date formatStartDate(String startDateStr){
        startDateStr+=" 00:00:00";
        return parseDate(startDateStr, io.dabing.common.util.DateUtils.FORMAT_DATE_TIME_YYYYMMdd);
    }
}
