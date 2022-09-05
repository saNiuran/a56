package com.niule.a56.calculator.utils.marquee;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hokaslibs.utils.Constant;
import com.niule.a56.calculator.R;

import java.util.List;

/**
 * RecyclerView实现跑马灯效果
 */
public class MarqueeView extends RecyclerView implements Handler.Callback {

    private boolean isScroll = false;

    private MarqueeClickListener marqueeClickListener;
    private final Handler marqueeHandler = new Handler(this);
//    private final SpannableStringBuilder marqueeSpannable = new SpannableStringBuilder();

    public MarqueeView(Context context) {
        this(context, null, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(lm);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == Constant.HANDLER_WHAT_LIVE_MARQUEE_SCROLL) {// 自动滚动(100ms)
            scrollBy(2, 2);
            if (isScroll)
                marqueeHandler.sendEmptyMessageDelayed(Constant.HANDLER_WHAT_LIVE_MARQUEE_SCROLL, 10);
        }
        return false;
    }

    private List<String> stringList;

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    /**
     * 设置数据并开始滚动
     */
    public void setMarqueeAdapter() {
        setAdapter(new MarqueeAdapter(stringList, R.layout.layout_custom_marquee_txt, new MarqueeCallBack<String>() {

            @Override
            public void onBindViewHolder(int position, MarqueeHolder holder, final String stock) {
                holder.setText(R.id.text, stock);
                holder.getMarquee(R.id.text).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 设置点击监听
                        marqueeClickListener.marqueeClickListener(stock);
                    }
                });
            }
        }));
    }

    /**
     * 设置初始化状态监听
     */
    public void setOnMarqueeClickListener(MarqueeClickListener marqueeClickListener) {
        this.marqueeClickListener = marqueeClickListener;
    }

    /**
     * 开始滚动
     */
    public void start() {
        if(!isScroll) {
            isScroll = true;
            marqueeHandler.sendEmptyMessageDelayed(Constant.HANDLER_WHAT_LIVE_MARQUEE_SCROLL, 100);
        }
    }

    //停止滚动
    public void stop() {
        isScroll = false;
    }
}