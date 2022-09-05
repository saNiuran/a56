package com.hokaslibs.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * 描述：日期工具类
 */
public class DateUtil {
    /**
     * 两个时间的差
     *
     * @param startDate
     * @param endDate
     * @return endDate - startDate;
     */
    public static String DateLength(String startDate, String endDate) {
        if (startDate != null && endDate != null) {
            long start = getString2Date(startDate);
            long end = getString2Date(endDate);
            int time = (int) ((end - start) / (24 * 60 * 60));
            return Integer.toString(time + 1) + "天";
        }
        return "未知";
    }

    /**
     * 两个String时间比较
     * 今天>=开始时间或结束时间	？ true ： false
     */
    public static boolean CompareDate(String today, String day) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null, dt2 = null;
        try {
            dt1 = sdf.parse(today);
            dt2 = sdf.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dt1.getTime() >= dt2.getTime();
    }

    // 获取当前时间，long型
    public static long currentTimeLong() {
        return new Date().getTime();
    }

    /**
     * 两个String时间比较
     *
     * @return date1>date2?true:false;
     */
    public static boolean DateSize(String date1, String date2) {

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null, dt2 = null;
        try {
            dt1 = sdf.parse(date1);
            dt2 = sdf.parse(date2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dt1.getTime() > dt2.getTime();
    }

    public static String getLiveTimeString(long startTime, long endTime) {
        return timeStampToStr2(startTime) + " - " + timeStampToStr2(endTime);
    }

    /**
     * 获取格林威治时间(unix时间戳 即1970年至今的秒数)
     */
    public static long getGMTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/Greenwich"));
        String format = sdf.format(new Date());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date gmDate = null;
        try {
            gmDate = sdf1.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gmDate.getTime() / 1000;
    }

    /**
     * 获取格林威治时间(unix时间戳 即1970年至今的秒数)
     */
    public static long _getGMTime() {
        int round = (int) (System.currentTimeMillis() / 1000);
        return round;
    }

//    /**
//     * 获取时间
//     *
//     * @return
//     */
//    public static String getCurrentTime() {
//        String time = null;
//        long round = System.currentTimeMillis() / 1000;
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = sdf.format(round * 1000);
//        String[] split = date.split("\\s");
//        if (split.length > 1) {
//            time = split[1];
//        }
//        return time;
//    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYestoryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 得到明天的日期
     *
     * @return
     */
    public static String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String tomorrow = sdf.format(calendar.getTime());
        return tomorrow;
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String date = sdf.format(timeStamp);
        return date;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr1(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String date = sdf.format(timeStamp);
        return date;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr2(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String date = sdf.format(timeStamp);
        return date;
    }

    /**
     * 时间戳转换时间格式
     */
    public static String timeStampDayToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp);
        return date;
    }

    /**
     * 时间戳转换时间格式
     */
    public static String timeStampDayToMDHM2(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(timeStamp);
        return date;
    }

    /**
     * 时间戳转换时间格式
     */
    public static String timeStampDayToMDHM(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        String date = sdf.format(timeStamp);
        return date;
    }

    public static String timelongChange(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String date = sdf.format(time);
        return date;
    }

    /**
     * 时间戳转换时间格式
     */
    public static String timeStampDayToStr2(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = sdf.format(timeStamp);
        return date;
    }


    /**
     * 时间戳转化为时间(几点)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToTime(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(timeStamp);
        return date;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate1(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd HH:mm");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将日期格式转化为时间戳(秒数)
     *
     * @param time
     * @return
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将日期格式转化为时间戳(秒数)
     *
     * @param time
     * @return
     */
    public static long getStringToDate1(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将日期格式转化为时间戳(秒数)
     *
     * @param time
     * @return
     */
    public static long getString2Date(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将日期格式转化为时间戳(秒数)
     *
     * @param time
     * @return
     */
    public static long getString2Date1(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将日期格式转化为时间戳(秒数)
     *
     * @param time
     * @return
     */
    public static long getString2Date2(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 判断是否大于当前时间
     *
     * @param time
     * @return
     */
    public static boolean judgeCurrTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sdf.parse(time);
            long t = date.getTime();
            long round = System.currentTimeMillis();
            if (t - round > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 判断是否大于当前时间
     *
     * @param time
     * @return
     */
    public static boolean judgeCurrTime(long time) {
        long round = System.currentTimeMillis();
        if (time - round > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较后面的时间是否大于前面的时间
     *
     * @param time1
     * @return
     */
    public static boolean judgeTime2Time(String time1, String time2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long l1 = date1.getTime() / 1000;
            long l2 = date2.getTime() / 1000;
            if (l2 - l1 > 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getLongTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 得到日期 yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String formatDate(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String date = sdf.format(timeStamp);
        return date;
    }


    /**
     * 得到时间 HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
//        long curTime = System.currentTimeMillis() ;
//        long time = curTime - timeStamp/1000;
//        if (time < 60 && time >= 0) {
//            return "刚刚";
//        } else if (time >= 60 && time < 3600) {
//            return time / 60 + "分钟前";
//        } else if (time >= 3600 && time < 3600 * 24) {
//            return time / 3600 + "小时前";
//        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
//            return time / 3600 / 24 + "天前";
//        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
//            return time / 3600 / 24 / 30 + "个月前";
//        } else if (time >= 3600 * 24 * 30 * 12) {
//            return time / 3600 / 24 / 30 / 12 + "年前";
//        } else {
//            return "刚刚";
//        }

        long time = curTime - timeStamp / 1000;
        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 4) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 4 ) {
            return HokasUtils.getDateToString3(timeStamp);
        } else {
            return "刚刚";
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @return
     */
    public static String convertTimeToFormat2(long time) {

        if (time < 60 && time >= 0) {
            return "刚刚刷新";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前刷新";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前刷新";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前刷新";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前刷新";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前刷新";
        } else {
            return "刚刚刷新";
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat3(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
//        long curTime = System.currentTimeMillis() ;
        long time = curTime - timeStamp / 1000;
        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else {
            return HokasUtils.getDateToString3(timeStamp);
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }

    /**
     * 获得当前时间差
     *
     * @param timeStamp
     * @return
     */
    public static int nowCurrentTime(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = timeStamp - curTime;
        return (int) time;
    }

    /**
     * @return
     */
    public static String nowCurrentPoint(boolean flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(System.currentTimeMillis());
        String[] split = date.split(":");
        String hour = null;
        String minute = null;
        if (flag) {
            if (split.length > 1) {
                hour = split[0];
                return hour;
            }
        } else {
            if (split.length > 1) {
                minute = split[1];
                return minute;
            }
        }
        return null;
    }

    /**
     * 将标准时间格式HH:mm:ss化为当前的时间差值
     *
     * @param str
     * @return
     */
    public static String StandardFormatStr(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(str);
            long timeStamp = d.getTime();

            long curTime = System.currentTimeMillis() / (long) 1000;
            long time = curTime - timeStamp / 1000;
            return time / 60 + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final int WEEKDAYS = 7;

    public static String[] WEEK = {"周日", "周一", "周二", "周三",
            "周四", "周五", "周六"};

    /**
     * 日期变量转成对应的星期字符串
     *
     * @param date
     * @return
     */
    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }


    /**
     * 日期变量转成对应的星期字符串
     *
     * @param timeStamp
     * @return
     */
    public static String DateToWeek(long timeStamp) {
        Date date = new Date(timeStamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }
        return WEEK[dayIndex - 1];
    }

    public static String convertGMTToLoacale(String gmt) {
        String s1 = gmt.substring(0, 10);
        String s2 = gmt.substring(11, 19);
        return s1 + " " + s2;
    }

    /**
     * 获得当前的lon类型时间
     *
     * @return
     */
    public static long getLongChatTime() {
        return System.currentTimeMillis();
    }

    /**
     * @param time
     * @return
     */
    public static String getChatTime(long time) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());//今天时间
        Date otherDay = new Date(time);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));
        switch (temp) {
            case 0:
                //result = "今天 " + getHourAndMin(time);
                result = getHourAndMin(time);
                break;
            case 1:
                //result = "昨天 " + getHourAndMin(time);
                result = "昨天 ";
                break;
            case 2:
                //result = "前天 " + getHourAndMin(time);
                result = "前天 ";
                break;
            default:
                // result = temp + "天前 ";
                result = formatDate(time);//年月日
                break;
        }
        return result;
    }

    /**
     * 判断消息间隔时长
     *
     * @param time1 最新时间
     * @param time2 上次时间
     * @return
     */
    public static boolean isCloseEnough(long time1, long time2) {
        long timeInterval = time1 - time2;
        if (timeInterval < 0L) {
            timeInterval = -timeInterval;
        }
        return timeInterval < 30000L;
    }

    /**
     * 当天凌晨毫秒数
     *
     * @return
     */
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        // date.getTime()是现在的毫秒数，它减去当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），
        // 理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
    }

    /**
     * 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 当前月份
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }


    public static String toTimeStr(int secTotal) {
        String result = null;
        int min = (secTotal % 3600) / 60;
        int sec = (secTotal % 3600) % 60;
        result = to2Str(min) + "'" + to2Str(sec) + "''";
        return result;
    }

    public static String to2Str(int i) {
        if (i > 9) {
            return i + "";
        } else {
            return "0" + i;
        }
    }

    //毫秒转秒
    public static String long2String(long time) {

        //毫秒转秒
        int sec = (int) time / 1000;
        int min = sec / 60;    //分钟
        sec = sec % 60;        //秒
        if (min < 10) {    //分钟补0
            if (sec < 10) {    //秒补0
                return "0" + min + ":0" + sec;
            } else {
                return "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {    //秒补0
                return min + ":0" + sec;
            } else {
                return min + ":" + sec;
            }
        }

    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(System.currentTimeMillis());
    }
}
