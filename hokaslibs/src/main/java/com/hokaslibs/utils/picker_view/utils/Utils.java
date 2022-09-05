package com.hokaslibs.utils.picker_view.utils;

import android.view.View;

import com.hokaslibs.utils.picker_view.date.WheelCalendar;


/**
 * 作者：Hokas  on 2016/6/24.
 * 邮箱：474711345@qq.com
 * 类别：
 */
public class Utils {
    public static boolean isTimeEquals(WheelCalendar calendar, int... params) {
        switch (params.length) {
            case 1:
                return calendar.year == params[0];
            case 2:
                return calendar.year == params[0] &&
                        calendar.month == params[1];
            case 3:
                return calendar.year == params[0] &&
                        calendar.month == params[1] &&
                        calendar.day == params[2];
            case 4:
                return calendar.year == params[0] &&
                        calendar.month == params[1] &&
                        calendar.day == params[2] &&
                        calendar.hour == params[3];
        }
        return false;
    }

    public static void hideViews(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
    }
}
