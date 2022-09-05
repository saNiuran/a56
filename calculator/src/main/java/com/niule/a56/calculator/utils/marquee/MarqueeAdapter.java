package com.niule.a56.calculator.utils.marquee;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * 跑马灯适配器
 */
public class MarqueeAdapter extends RecyclerView.Adapter<MarqueeHolder> {

    private int layoutId;
    private List<String> list;
    private MarqueeCallBack recyclerCallBack;

    public MarqueeAdapter(final List<String> list, final int layoutId,
                          final MarqueeCallBack recyclerCallBack) {
        this.list = list;
        this.layoutId = layoutId;
        this.recyclerCallBack = recyclerCallBack;
    }

    @Override
    public MarqueeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new MarqueeHolder(LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MarqueeHolder viewHolder, int position) {
        recyclerCallBack.onBindViewHolder(position, viewHolder, list.get(position % list.size()));
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return Integer.MAX_VALUE;
    }
}