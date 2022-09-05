package com.hokaslibs.utils.screening;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiiu.filter.adapter.BaseBaseAdapter;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.bumptech.glide.Glide;
import com.hokaslibs.R;

import java.util.List;

/**
 * Created by administor on 2017/7/13.
 */

public abstract class NationwideAdapter<T> extends BaseBaseAdapter<T> {

    private final LayoutInflater inflater;

    public NationwideAdapter(List<T> list, Context context) {
        super(list, context);
        inflater = LayoutInflater.from(context);
    }

    public static class FilterItemHolder {
        TextView checkedTextView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilterItemHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_hy_sx, parent, false);

            holder = new FilterItemHolder();
            holder.checkedTextView = convertView.findViewById(R.id.tvHY);
            holder.imageView = convertView.findViewById(R.id.ivHY);
//            holder.checkedTextView.setPadding(0, UIUtil.dp(context, 15), 0, UIUtil.dp(context, 15));

            convertView.setTag(holder);
        } else {
            holder = (FilterItemHolder) convertView.getTag();
        }

        T t = list.get(position);
        holder.checkedTextView.setText(provideText(t));
        if("全部".equals(provideText(t)) || "全行业".equals(provideText(t))){
            Glide.with(context).load(provideIconInt(t)).into(holder.imageView);
        }else {
            Glide.with(context).load(provideIcon(t)).into(holder.imageView);
        }
        initCheckedTextView(holder.checkedTextView);

        return convertView;
    }

    public abstract String provideText(T t);
    public abstract String provideIcon(T t);
    public abstract int provideIconInt(T t);

    protected void initCheckedTextView(TextView checkedTextView) {
    }

    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
    }

}
