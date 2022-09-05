package com.hokaslibs.utils.picker_view;


import com.hokaslibs.utils.picker_view.date.WheelCalendar;

/**
 * 作者：Hokas  on 2016/6/24.
 * 邮箱：474711345@qq.com
 * 类别：
 */
public interface IController {
    int getMinYear();

    int getMaxYear();

    int getMinMonth(int currentYear);

    int getMaxMonth();

    int getMinDay(int year, int month);

    int getMaxDay(int year, int month);

    int getMinHour(int year, int month, int day);

    int getMaxHour();

    int getMinMinute(int year, int month, int day, int hour);

    int getMaxMinute();

    boolean isMinYear(int year);

    boolean isMinMonth(int year, int month);

    boolean isMinDay(int year, int month, int day);

    boolean isNoRange();

    WheelCalendar getDefaultCalendar();
}
