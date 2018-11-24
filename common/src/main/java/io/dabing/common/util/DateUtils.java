package io.dabing.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static void main(String[] args) {
		System.out.println("x:" + DateUtils.format(new Date(), DateUtils.FORMAT_DATE_YYMMDD)+":");


		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, 20);

		long days = DateUtils.getBetweenDayIgnoreTime(date, calendar.getTime());


		System.out.println(days);
	}



	public static String FORMAT_DATE_TIME_YYYYMMdd="yyyyMMdd HH:mm:ss";

	public static String FORMAT_DATE_TIME_DEFAULT="yyyy-MM-dd HH:mm:ss";
	
	public static String FORMAT_DATE_DEFAULT="yyyy-MM-dd";

	public static String FORMAT_DATE_YYYY_MM_DD="yyyy-MM-dd";

	public static String FORMAT_DATE_TIME_YYYYMMDDHHMMSS="yyyyMMddHHmmss";

	public static String FORMAT_DATE_TIME_YYYYMMDDHHMM="yyyyMMddHHmm";
	
	public static String FORMAT_DATE_YYYYMMDD="yyyyMMdd";

	public static String FORMAT_DATE_YYMMDD="yyMMdd";

	public static String FORMAT_DATE_HHMMSS="HHmmss";

	public static Date parseDate(String text,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		try {
			return sdf.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(Date date,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
	    return sdf.format(date);
	}
	
	
	public static String getYear(Date date){
		String dateStr=format(date, FORMAT_DATE_DEFAULT);
		String[] array=dateStr.split("-");
		return array[0];
	}
	public static String getMonth(Date date){
		String dateStr=format(date, FORMAT_DATE_DEFAULT);
		String[] array=dateStr.split("-");
		return array[1];
	}
	public static String getDate(Date date){
		String dateStr=format(date, FORMAT_DATE_DEFAULT);
		String[] array=dateStr.split("-");
		return array[2];
	}
	
	 public static Date getMonthStart(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        int index = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar.add(Calendar.DATE, (1 - index));
	        return calendar.getTime();
	    }
	 
	 public static Date getMonthEnd(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MONTH, 1);
	        int index = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar.add(Calendar.DATE, (-index));
	        return calendar.getTime();
	  }
	 /**
	  * 得到上个月的月初
	  * @param date
	  * @return
	  */
	 public static Date getLastMonthStart(Date date) {
		 Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.MONTH, -1);
         int index = calendar.get(Calendar.DAY_OF_MONTH);
         calendar.add(Calendar.DATE, (1 - index));
         return calendar.getTime();
	  }
	 /**
	  * 得到上个月的月末
	  * @param date
	  * @return
	  */
	 public static Date getLastMonthEnd(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MONTH,0);
	        int index = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar.add(Calendar.DATE, (-index));
	        return calendar.getTime();
	  }
	 
	 public static Date getNext(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, 1);
	        return calendar.getTime();
	 }
	 
	 public static Date getYesterday(Date date){
		  Calendar cal=Calendar.getInstance();
		  cal.setTime(date);
		  cal.add(Calendar.DATE,-1);
		  return cal.getTime();
	 }

	public static Date getDayBefore(Date date,int offset){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,-offset);
		return cal.getTime();
	}
	 
	 /**
	  * 得到上周一
	  * @param date
	  * @return
	  */
	 public static Date getLastMonday(Date date) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
		    cal.add(Calendar.DATE, -1*7);
		    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		    return cal.getTime();
	}
	 /**
	  * 得到上周日
	  * @param date
	  * @return
	  */
	 public static Date getLastSunday(Date date) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
		    cal.add(Calendar.DATE, -1*7);
		    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		    return cal.getTime();
	 }


	public static long getBetweenDay(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		long days = (d2.getTime().getTime() - d1.getTime().getTime()) / 86400000L;
		return days;
	}


	public static long getBetweenDayIgnoreTime(Date startDate, Date endDate) {
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		d1.set(Calendar.HOUR_OF_DAY, 0);
		d1.set(Calendar.MINUTE, 0);
		d1.set(Calendar.SECOND, 0);

		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		d2.set(Calendar.HOUR_OF_DAY, 0);
		d2.set(Calendar.MINUTE, 0);
		d2.set(Calendar.SECOND, 0);

		long days = (d2.getTime().getTime() - d1.getTime().getTime()) / 86400000L;


		return days;
	}



	/**
	 * 校验日期格式
	 * @param text
	 * @param format
     * @return
     */
	public static boolean isValidDate(String text,String format) {
		return parseDate(text,format)!=null;
	}


}
