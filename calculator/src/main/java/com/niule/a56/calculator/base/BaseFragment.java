package com.niule.a56.calculator.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import com.niule.a56.calculator.utils.PreferencesUtil;

public abstract class BaseFragment extends XFragment {

    protected Activity mContext;

    protected long refreshTime = 200;
    protected long loadTime = 500;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getContext() != null && PreferencesUtil.getDataBoolean("userAgreement")) {
            JAnalyticsInterface.onPageStart(getContext(), getClass().getName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getContext() != null && PreferencesUtil.getDataBoolean("userAgreement")) {
            JAnalyticsInterface.onPageEnd(getContext(), getClass().getName());
        }
    }

}
