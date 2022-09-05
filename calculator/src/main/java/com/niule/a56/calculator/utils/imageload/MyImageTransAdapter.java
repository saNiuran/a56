package com.niule.a56.calculator.utils.imageload;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.viewpager.widget.ViewPager;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.base.BaseApplication;
import com.niule.a56.calculator.R;

import it.liuting.imagetrans.ImageTransAdapter;


/**
 * Created by liuting on 17/6/15.
 */

public class MyImageTransAdapter extends ImageTransAdapter {
    private View view;
    private View topPanel;
    private RoundPageIndicator bottomPanel;
    private boolean isShow = true;

    @Override
    protected View onCreateView(View parent, ViewPager viewPager, final DialogInterface dialogInterface) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_trans_adapter, null);
        topPanel = view.findViewById(R.id.top_panel);
        bottomPanel = (RoundPageIndicator) view.findViewById(R.id.page_indicator);
        view.findViewById(R.id.top_panel_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInterface.cancel();
            }
        });
        topPanel.setTranslationY(-BaseApplication.dpToPx(56));
        bottomPanel.setTranslationY(BaseApplication.dpToPx(80));
        bottomPanel.setViewPager(viewPager);
        return view;
    }

    @Override
    public void onPullRange(float range) {
        HokasUtils.logcat("onPullRange");
        topPanel.setTranslationY(-BaseApplication.dpToPx(56) * range * 4);
        bottomPanel.setTranslationY(BaseApplication.dpToPx(80) * range * 4);
    }

    @Override
    public void onPullCancel() {
        showPanel();
    }

    @Override
    protected void onOpenTransStart() {
        showPanel();
    }

    @Override
    protected void onOpenTransEnd() {

    }

    @Override
    protected void onCloseTransStart() {
        hiddenPanel();
    }

    @Override
    protected void onCloseTransEnd() {
        TileBitmapDrawable.clearCache();
    }

    @Override
    protected boolean onClick(View v, int pos) {
        if (isShow) {
            showPanel();
        } else {
            hiddenPanel();
        }
        isShow = !isShow;

        return true;
    }

    @Override
    protected void onLongClick(View v, int pos) {
//        UiUtils.makeText("long click");
    }

    public void hiddenPanel() {
        HokasUtils.logcat("hiddenPanel");
        topPanel.animate().translationY(-BaseApplication.dpToPx(56)).setDuration(200).start();
        bottomPanel.animate().translationY(BaseApplication.dpToPx(80)).setDuration(200).start();
    }

    public void showPanel() {
        HokasUtils.logcat("showPanel");
        topPanel.animate().translationY(0).setDuration(200).start();
        bottomPanel.animate().translationY(0).setDuration(200).start();
    }

}
