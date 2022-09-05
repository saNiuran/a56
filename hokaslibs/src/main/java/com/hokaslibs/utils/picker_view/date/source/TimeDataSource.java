package com.hokaslibs.utils.picker_view.date.source;


import com.hokaslibs.utils.picker_view.date.WheelCalendar;

/**
 * 作者：Hokas  on 2016/6/24.
 * 邮箱：474711345@qq.com
 * 类别：
 */
public interface TimeDataSource {

    int getMinYear();

    int getMaxYear();

    int getMinMonth(int currentYear);

    int getMaxMonth(int currentYear);

    int getMinDay(int year, int month);

    int getMaxDay(int year, int month);

    int getMinHour(int year, int month, int day);

    int getMaxHour(int year, int month, int day);

    int getMinMinute(int year, int month, int day, int hour);

    int getMaxMinute(int year, int month, int day, int hour);

    boolean isMinYear(int year);

    boolean isMinMonth(int year, int month);

    boolean isMinDay(int year, int month, int day);

    boolean isMinHour(int year, int month, int day, int hour);
//
//    boolean isMaxYear(int year);
//
//    boolean isMaxMonth(int year, int month);
//
//    boolean isMaxDay(int year, int month, int day);
//
//    boolean isMaxMinute(int year, int month, int day, int hour);

    WheelCalendar getDefaultCalendar();

}
