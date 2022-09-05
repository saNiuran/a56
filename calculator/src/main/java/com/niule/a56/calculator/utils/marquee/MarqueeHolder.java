package com.niule.a56.calculator.utils.marquee;


import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MarqueeHolder extends RecyclerView.ViewHolder {

    public MarqueeHolder(View itemView) {
        super(itemView);
    }

    public TextView getMarquee(int viewId) {
        return (TextView) itemView.findViewById(viewId);
    }

    public MarqueeHolder setText(int viewId, SpannableStringBuilder text) {
        TextView view = getMarquee(viewId);
        view.setText(text);
        return this;
    }
    public MarqueeHolder setText(int viewId, String text) {
        TextView view = getMarquee(viewId);
        view.setText(text);
        return this;
    }
}