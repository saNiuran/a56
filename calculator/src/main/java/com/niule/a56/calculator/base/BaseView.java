package com.niule.a56.calculator.base;

import android.content.Intent;

/**
 * 作者： Hokas
 * 时间： 2017/1/5
 * 类别：
 */

public interface BaseView {
    /**
     * 成功加载数据
     */
    void onSuccess();

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);

    /**
     * 跳转activity
     */
    void launchActivity(Intent intent);

    /**
     * 杀死自己
     */
    void killMyself();

    /**
     * 数据加载错误
     */
    void onError(String errMsg);
}
