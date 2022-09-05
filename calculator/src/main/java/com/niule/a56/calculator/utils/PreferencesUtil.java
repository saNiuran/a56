package com.niule.a56.calculator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.base.XApplication;
import com.niule.a56.calculator.bean.Options;


/**
 * 获取参数工具
 */
public class PreferencesUtil {

    private static final String PREFERENCES_NAME = "yunjiagongcat";
    private static final String KEY_TOKEN = "token";

    private static SharedPreferences getDefaultSharedPreferences() {
        XApplication application = XApplication.getInstance();
        SharedPreferences sp = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * 获取boolean数据
     */
    public static boolean getDataBoolean(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getBoolean(key, false);
    }

    /**
     * 保存boolean数据
     */
    public static void setDataBoolean(String key, boolean data) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putBoolean(key, data).apply();
    }


    /**
     * 获取字符串数据
     */
    public static String getDataString(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getString(key, null);
    }

    /**
     * 保存字符串数据
     */
    public static void setDataString(String key, String data) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString(key, data).apply();
    }

    /**
     * 删除字符串信息
     */
    public static boolean removeDataString(String key) {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.edit().remove(key).commit();
    }

    /**
     * 获取Options
     */
    public static Options getOptions() {
        SharedPreferences sp = getDefaultSharedPreferences();
        String options = sp.getString("options", null);
        if(options==null){
            return null;
        }
        return new Gson().fromJson(options,new TypeToken<Options>(){}.getType());
    }

    /**
     * 保存Options
     */
    public static void setOptions(Options data) {
//        HokasUtils.logcat("保存前"+data);
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString("options", new Gson().toJson(data)).apply();

//        HokasUtils.logcat("保存后读出字符串="+getOptions());
    }

    /**
     * 删除Options
     */
    public static boolean removeOptions() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.edit().remove("options").commit();
    }


    /**
     * 获取token
     */
    public static String getToken() {
        SharedPreferences sp = getDefaultSharedPreferences();
        return sp.getString(KEY_TOKEN, "1");
    }

    /**
     * 设置token
     */
    public static void setToken(String token) {
        SharedPreferences sp = getDefaultSharedPreferences();
        sp.edit().putString(KEY_TOKEN, token).apply();
    }

}
