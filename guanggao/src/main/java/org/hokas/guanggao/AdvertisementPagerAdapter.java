package org.hokas.guanggao;

import android.content.Context;
import androidx.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> sourceData = new ArrayList<String>();

    public AdvertisementPagerAdapter(Context context) {
        this.context = context;
        initData();
    }

    /**
     * 初始化原始数据
     */
    public void initData() {
        sourceData.clear();
        for (int i = 0; i < 5; i++) {
            sourceData.add(i + "");
        }
    }

    @Override
    public int getCount() {
        return sourceData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view;
        if (position % 2 == 0) {
            view = View.inflate(context, R.layout.item_pager_one, null);
        } else {
            view = View.inflate(context, R.layout.item_pager_two, null);
        }
        view.setTag(String.valueOf(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
