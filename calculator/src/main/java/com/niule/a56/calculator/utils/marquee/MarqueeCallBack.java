package com.niule.a56.calculator.utils.marquee;

/** 我的 */
public interface MarqueeCallBack<T> {

	void onBindViewHolder(int position, MarqueeHolder viewHolder, T t);
}