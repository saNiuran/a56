package com.hokaslibs.utils.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

/**
 * @author: 蜡笔小新
 * @date: 2016-05-31 14:15
 * @GitHub: https://github.com/meikoz
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        mContext = context;
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (view != null)
            view.setText(text);
        return this;
    }

    /**
     * 为TextView设置字符串，颜色
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text, int resId) {
        TextView view = getView(viewId);
        view.setTextColor(resId);
        view.setText(text);
        return this;
    }

    /**
     * 为TextView设置字符串，颜色
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setTextBackGroud(int viewId, int resId) {
        TextView view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param
     * @param
     * @return
     */
//    public ViewHolder setImageByUrl(int viewId, String url) {
//        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));
//        return this;
//    }
    public int getPosition() {
        return mPosition;
    }


    public void setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 关于点击事件的
     */
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(ContextCompat.getColor(mContext,textColor));
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(ContextCompat.getColor(mContext,color));
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }
}
