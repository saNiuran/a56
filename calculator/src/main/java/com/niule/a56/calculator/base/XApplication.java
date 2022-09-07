package com.niule.a56.calculator.base;

import android.app.Activity;
import android.app.Application;
import com.blankj.utilcode.util.Utils;
import com.hokaslibs.BuildConfig;
import timber.log.Timber;

import java.util.Stack;

/**
 * 作者： Hokas
 * 时间： 2017/1/3
 * 类别：
 */

public class XApplication extends Application {
    private final String TAG = "XApplication";
    public static Stack<Activity> activityList = new Stack<>();

    private int mActivityCount = 0;
    private static XApplication instance;

    public static XApplication getInstance() {
        if (instance == null) {
            instance = new XApplication();
            return instance;
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        if (BuildConfig.LOG_DEBUG) {//Timber日志打印
            Timber.plant(new Timber.DebugTree());
        }
    }

    /**
     * 添加到Activity容器中
     */
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 遍历所有Activity并finish
     */
    public static void finishActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    /**
     * 结束指定的Activity
     */
    public static void finishSingleActivity(Activity activity) {
        if (activity != null) {
            if (activityList.contains(activity)) {
                activityList.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作，所有我们先记住要删除的对象，遍历之后才去删除。
     */
    public static void finishSingleActivityByClass(Class<?> cls) {
        Activity tempActivity = null;
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                tempActivity = activity;
            }
        }

        finishSingleActivity(tempActivity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if(activityList!=null) {
            Activity activity = activityList.lastElement();
            if (activity != null)
                return activity;
            else return null;
        }
        else return null;
    }
}
