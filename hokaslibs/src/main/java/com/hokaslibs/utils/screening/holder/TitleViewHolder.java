package com.hokaslibs.utils.screening.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.baiiu.filter.util.UIUtil;
import com.hokaslibs.R;


/**
 * auther: baiiu
 * time: 16/6/5 05 23:30
 * description:
 */
public class TitleViewHolder extends RecyclerView.ViewHolder {

    public TitleViewHolder(Context mContext, ViewGroup parent) {
        super(UIUtil.infalte(mContext, R.layout.holder_title, parent));
    }


    public void bind(String s) {
        ((TextView) itemView.findViewById(R.id.textView)).setText(s);
    }
}
