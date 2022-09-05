package com.hokaslibs.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/5/15.
 */
public class MyViewGroup extends ViewGroup {
    private final static String TAG = "MyViewGroup";

    private final static int VIEW_MARGIN = 20;
    private int lengthY;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d(TAG, "widthMeasureSpec = " + widthMeasureSpec
//                + " heightMeasureSpec" + heightMeasureSpec);

        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            // measure
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

        }
        setMeasuredDimension(widthMeasureSpec, lengthY + 20);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "changed = " + changed + " left = " + l + " top = " + t
                + " right = " + r + " bottom = " + b);

        final int count = getChildCount();
        int row = 0;// which row lay you view relative to parent
        int lengthX = l; // right position of child relative to parent
        lengthY = t; // bottom position of child relative to parent
        for (int i = 0; i < count; i++) {
            final View child = this.getChildAt(i);

            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            lengthX += width;
            lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + t;
            // if it can't drawing on a same line , skip to next line
            if (lengthX > r) {
                lengthX = width;
                row++;
                lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + t;
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
        }
    }
}
