package com.hokaslibs.adapter;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者： Hokas
 * 时间： 2016/10/17
 * 类别：
 */

public class BaseViewPager2Adapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public BaseViewPager2Adapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        //处理position。让数组下标落在[0,fragmentList.size)中，防止越界
        position = position % fragmentList.size();

        return super.instantiateItem(container, position);
    }
}
