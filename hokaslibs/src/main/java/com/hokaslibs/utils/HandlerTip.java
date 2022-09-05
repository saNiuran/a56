package com.hokaslibs.utils;

import android.os.Handler;

/**
 * @author: 蜡笔小新
 * @date: 2016-06-21 13:59
 * @GitHub: https://github.com/meikoz
 */
public class HandlerTip {

    private static final HandlerTip mDelayed = new HandlerTip();
    private final Handler handler;

    public HandlerTip() {
        handler = new Handler();
    }


    public static HandlerTip getInstance() {
        return mDelayed;
    }

    public Handler getHandler() {
        return handler;
    }

    public void postDelayed(long time, final HandlerCallback call) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                call.postDelayed();
            }
        }, time);
    }

    public interface HandlerCallback {
        void postDelayed();
    }
}
