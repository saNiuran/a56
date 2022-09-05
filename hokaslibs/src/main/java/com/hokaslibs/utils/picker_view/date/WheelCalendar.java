package com.hokaslibs.utils.picker_view.date;

import java.util.Calendar;

/**
 * 作者：Hokas  on 2016/6/24.
 * 邮箱：474711345@qq.com
 * 类别：
 */
public class WheelCalendar {
    public int year, month, day, hour, minute;

    private boolean noRange;

    public WheelCalendar(long millseconds) {
        initData(millseconds);
    }

    private void initData(long millseconds) {
        if (millseconds == 0) {
            noRange = true;
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(millseconds);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public boolean isNoRange() {
        return noRange;
    }
}
