package com.niule.a56.calculator.base;

import rx.Subscription;

/**
 * 作者： Hokas
 * 时间： 2017/1/5
 * 类别：
 */

public interface presenter {
    void onStart();
    void onDestroy();
    void unSubscribe(Subscription subscription);
}
