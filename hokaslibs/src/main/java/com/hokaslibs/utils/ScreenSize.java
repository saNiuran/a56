package com.hokaslibs.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @Author Rich on 2020-02-08 1:48
 * @Projcet cat
 */
public class ScreenSize {

    private int width;         // 屏幕宽度（像素）
    private int height;       // 屏幕高度（像素）
    private float density;         // 屏幕密度（0.75 / 1.0 / 1.5）
    private int densityDpi;

    public ScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        this.width = dm.widthPixels;
        this.height = dm.heightPixels;
        this.density = dm.density;
        this.densityDpi = dm.densityDpi;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(int densityDpi) {
        this.densityDpi = densityDpi;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
