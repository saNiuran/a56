package com.niule.a56.calculator.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.hokaslibs.utils.HokasUtils;
import com.hokaslibs.utils.recycler.XRecyclerAdapter;
import com.hokaslibs.utils.recycler.XRecyclerViewHolder;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.bean.GeneralMemo;
import com.niule.a56.calculator.bean.PriceExtra;

import java.util.List;

public class MemoAdapter extends XRecyclerAdapter<GeneralMemo> {

    public MemoAdapter(Context context, int layoutId, List<GeneralMemo> memoList) {
        super(context, layoutId, memoList);
    }

    @Override
    public void convert(XRecyclerViewHolder holder, GeneralMemo generalMemo, int position) {
        if(holder!=null && generalMemo!=null){
//
//            TextView tvItemNo = holder.getConvertView().findViewById(R.id.tvItemNo);
//            tvItemNo.setText(position+1+",");

            TextView tvItemMemo = holder.getConvertView().findViewById(R.id.tvItemMemo);
            tvItemMemo.setText(generalMemo.getMemo().trim());
        }
    }
}
