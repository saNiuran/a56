package com.hokaslibs.utils.screening;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;
import com.hokaslibs.utils.screening.holder.ItemViewHolder;
import com.hokaslibs.utils.screening.holder.TitleViewHolder;

import java.util.List;

/**
 * auther: baiiu
 * time: 16/6/5 05 23:28
 * description:
 */
public class DoubleGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<String> topGridData;
    private List<String> bottomGridData;
    private List<String> jiaoqiGridData;
    private View.OnClickListener mListener;

    public DoubleGridAdapter(Context context, List<String> topGridData, List<String> bottomGridList,
                             List<String> jiaoqiGridList, View.OnClickListener listener) {
        this.mContext = context;
        this.topGridData = topGridData;
        this.bottomGridData = bottomGridList;
        this.jiaoqiGridData = jiaoqiGridList;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == topGridData.size() + 1 || position == topGridData.size() + bottomGridData.size() + 2) {
            return TYPE_TITLE;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case TYPE_TITLE:
                holder = new TitleViewHolder(mContext, parent);
                break;
            case TYPE_ITEM:
                holder = new ItemViewHolder(mContext, parent, mListener);
                break;
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_TITLE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                if (position == 0) {
                    titleViewHolder.bind("最新");
                } else if (position == topGridData.size() + 1) {
                    titleViewHolder.bind("总金额");
                } else
                    titleViewHolder.bind("交期");
                break;
            case TYPE_ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                if (position < topGridData.size() + 1) {
                    itemViewHolder.bind(topGridData.get(position - 1));
                } else if (topGridData.size() + 1 < position && position < topGridData.size() + bottomGridData.size() + 2) {
                    itemViewHolder.bind(bottomGridData.get(position - topGridData.size() - 2));
                } else {
                    int num = position - topGridData.size() - bottomGridData.size() - 3;
                    itemViewHolder.bind(jiaoqiGridData.get(num));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return topGridData.size() + bottomGridData.size() + jiaoqiGridData.size() + 3;
    }
}
