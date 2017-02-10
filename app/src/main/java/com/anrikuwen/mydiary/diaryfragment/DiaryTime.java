package com.anrikuwen.mydiary.diaryfragment;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 10393 on 2017/2/9.
 */

public class DiaryTime {

    private static String year;
    private static String month;
    private static String day;
    private static String weekDay;
    private static String time;

    public static String getYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    public static String getMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if("1".equals(month)){
            month = "一月";
        }else if ("2".equals(month)){
            month = "二月";
        }else if ("3".equals(month)){
            month = "三月";
        }else if ("4".equals(month)){
            month = "四月";
        }else if ("5".equals(month)){
            month = "五月";
        }else if ("6".equals(month)){
            month = "六月";
        }else if ("7".equals(month)){
            month = "七月";
        }else if ("8".equals(month)){
            month = "八月";
        }else if ("9".equals(month)){
            month = "九月";
        }else if ("10".equals(month)){
            month = "十月";
        }else if ("11".equals(month)){
            month = "十一月";
        }else if ("12".equals(month)){
            month = "十二月";
        }
        return month;
    }

    public static String getWeekDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        weekDay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        if("1".equals(weekDay)){
            weekDay = "星期天";
        }else if("2".equals(weekDay)){
            weekDay = "星期一";
        }else if("3".equals(weekDay)){
            weekDay = "星期二";
        }else if ("4".equals(weekDay)){
            weekDay = "星期三";
        }else if ("5".equals(weekDay)){
            weekDay = "星期四";
        }else if ("6".equals(weekDay)){
            weekDay = "星期五";
        }else if ("7".equals(weekDay)){
            weekDay = "星期六";
        }
        return weekDay;
    }

    public static String getDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
       day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return day;
    }

    public static String getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        time = hour + ":" + minute + ":" + second;
        return time;
    }
}
