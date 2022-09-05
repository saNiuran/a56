package com.niule.a56.calculator.utils.imageload;

import android.content.Context;
import android.view.ViewGroup;

import com.niule.a56.calculator.base.BaseApplication;

import it.liuting.imagetrans.listener.ProgressViewGet;


/**
 * Created by liuting on 18/3/19.
 */

public class MyProgressBarGet implements ProgressViewGet<RingLoadingView> {
    @Override
    public RingLoadingView getProgress(Context context) {
        RingLoadingView view = new RingLoadingView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(BaseApplication.dpToPx(50), BaseApplication.dpToPx(50)));
        return view;
    }

    @Override
    public void onProgressChange(RingLoadingView view, float progress) {
        view.setProgress(progress);
    }
}
