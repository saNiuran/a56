package com.hokaslibs.utils.screening.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiiu.filter.adapter.BaseBaseAdapter;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.hokaslibs.R;

import java.util.List;

/**
 * Created by baiiu on 15/12/23.
 * 菜单条目适配器
 */
public abstract class SimpleText2Adapter<T> extends BaseBaseAdapter {

    private final LayoutInflater inflater;

    public SimpleText2Adapter(List<T> list, Context context) {
        super(list, context);
        inflater = LayoutInflater.from(context);
    }

    public static class FilterItemHolder {
        FilterCheckedTextView checkedTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilterItemHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lv_item_filter2, parent, false);

            holder = new FilterItemHolder();
            holder.checkedTextView = (FilterCheckedTextView) convertView.findViewById(R.id.tv_item_filter);
//            holder.checkedTextView.setPadding(0, UIUtil.dp(context, 15), 0, UIUtil.dp(context, 15));
//            initCheckedTextView(holder.checkedTextView);

            convertView.setTag(holder);
        } else {
            holder = (FilterItemHolder) convertView.getTag();
        }

//        if (position == 0) {
//            convertView.findViewById(R.id.view).setVisibility(View.GONE);
//        } else
//            convertView.findViewById(R.id.view).setVisibility(View.VISIBLE);

        T t = (T) list.get(position);
        holder.checkedTextView.setText(provideText(t));

        initCheckedTextView(holder.checkedTextView);

//        if(t.isFlag()){
//            holder.checkedTextView.setChecked(true);
//        }else {
//            holder.checkedTextView.setChecked(false);
//        }
        return convertView;
    }

    public abstract String provideText(T t);

    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
    }


}
