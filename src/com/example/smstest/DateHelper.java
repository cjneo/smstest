package com.example.smstest;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static String today;
    public static String lastYearToday;

    public static Date getIndexDay( Date origin,int index) {
        Date dt=origin;
        
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
      //  rightNow.add(Calendar.YEAR,-1);//日期减1年
       // rightNow.add(Calendar.MONTH,3);//日期加3个月
        rightNow.add(Calendar.DAY_OF_YEAR,index);//日期加10天
        Date dt1=rightNow.getTime();
       // String reStr = sdf.format(dt1);
        //System.out.println(reStr);
        return dt1;
    }
    public static Date getIndexYear( Date origin,int index) {
        Date dt=origin;
        
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,index);//日期减1年
       // rightNow.add(Calendar.MONTH,3);//日期加3个月
        Date dt1=rightNow.getTime();
       // String reStr = sdf.format(dt1);
        //System.out.println(reStr);
        return dt1;
    }
    public static String getStrFromDate(Date date){
        Date curDate = date;
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        today = sdf.format(curDate);
        // today = sdf.format(1000);
        return today;
        
    }
    public static Date getDateToday() {
        Date curDate = new Date();// 获取当前时间
        
        return curDate;
    }
    public static String getToday() {
        Date curDate = new Date();// 获取当前时间
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        today = sdf.format(curDate);
        // today = sdf.format(1000);
        return today;
    }

    public static String getStringOfLong(long date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        today = sdf.format(date);
        // today = sdf.format(1000);
        return today;
    }

    public static String getLastyearToday(String str) {

        String subYear = str.substring(0, 4);
        String subMonth = str.substring(5, 7);
        String subDay = str.substring(8, 10);

        Long LongYear = new Long(subYear);
        long longYear = LongYear.longValue();
        longYear--;
        lastYearToday = longYear + "-" + subMonth + "-" + subDay;
        return lastYearToday;
    }

    // yyyy-MM-dd
    public static String getyesterday(String today) {

        String subYear = today.substring(0, 4);
        String subMonth = today.substring(5, 7);
        String subDay = today.substring(8, 10);

        Long LongDay = new Long(subDay);
        long longDay = LongDay.longValue();

        if (longDay != 1) {
            longDay--;
        }
        String yesterday ;
        if (longDay < 10) {
            yesterday = subYear + "-" + subMonth + "-" + "0" + longDay;
       } else {
            yesterday = subYear + "-" + subMonth + "-" + longDay;
       }
        return yesterday;
    }

    public static String gettomorrow(String today) {

        String subYear = today.substring(0, 4);
        String subMonth = today.substring(5, 7);
        String subDay = today.substring(8, 10);

        Long LongDay = new Long(subDay);
        long longDay = LongDay.longValue();

        if (longDay != 30) {
            longDay++;
        }
        String tomorrow;
        if (longDay < 10) {
             tomorrow = subYear + "-" + subMonth + "-" + "0" + longDay;
        } else {
             tomorrow = subYear + "-" + subMonth + "-" + longDay;
        }
        return tomorrow;
    }

    public static String getLastMonth(String today) {

        String subYear = today.substring(0, 4);
        String subMonth = today.substring(5, 7);
        String subDay = today.substring(8, 10);

        Long LongMonth = new Long(subMonth);
        long longMonth = LongMonth.longValue();

        Long LongYear = new Long(subYear);
        long longYear = LongYear.longValue();

        String tomorrow;

        if (longMonth != 1) {
            longMonth--;
            if (longMonth < 10) {
                tomorrow = subYear + "-" + "0" + longMonth + "-" + subDay;
            } else
                tomorrow = subYear + "-" + longMonth + "-" + subDay;
        } else {
            longMonth = 12;
            longYear--;
            tomorrow = longYear + "-" + longMonth + "-" + subDay;
        }
        return tomorrow;
    }

    public static String getThisMonth(String today) {

        String subYear = today.substring(0, 4);
        String subMonth = today.substring(5, 7);
        String subDay = today.substring(8, 10);

        Long LongMonth = new Long(subMonth);
        long longMonth = LongMonth.longValue();

        Long LongYear = new Long(subYear);
        long longYear = LongYear.longValue();

        String tomorrow;
        tomorrow = longYear + "-" + longMonth + "-" + "01";
        return tomorrow;
    }

    public static long getLongOfString(String date) {
        String dateStr2 = date;
        ParsePosition pos2 = new ParsePosition(0);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        Date strtodate2 = formatter2.parse(dateStr2, pos2);
        long dateLong2 = strtodate2.getTime();
        return dateLong2;
    }
}