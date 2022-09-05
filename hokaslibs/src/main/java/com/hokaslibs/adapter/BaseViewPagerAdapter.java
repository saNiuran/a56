package com.hokaslibs.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者： Hokas
 * 时间： 2016/10/17
 * 类别：
 */

public class BaseViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> list_Title;

    public BaseViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> list_Title) {
        super(fm);
        this.fragmentList = fragmentList;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        if (list_Title != null) {
            return list_Title.get(position % list_Title.size());
        }
        return super.getPageTitle(position);
    }
}
