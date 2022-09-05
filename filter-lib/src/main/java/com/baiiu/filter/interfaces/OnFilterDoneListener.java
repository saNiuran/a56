package com.baiiu.filter.interfaces;

/**
 * author: baiiu
 * date: on 16/1/21 23:30
 * description:
 */
public interface OnFilterDoneListener {
    void onOne(int position, String title);

    void onTwo(int position, String title, String sortSign);

}