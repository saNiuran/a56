//package com.hokaslibs.utils.recycler;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.recyclerview.widget.RecyclerView;
//import com.google.gson.Gson;
//
//import java.util.List;
//
///**
// * @author: 蜡笔小新
// * @date: 2016-05-31 14:15
// * @GitHub: https://github.com/meikoz
// */
//public abstract class XRecyclerAdapterJia2<T> extends RecyclerView.Adapter<XRecyclerViewHolder> {
//    protected Context mContext;
//    protected int mLayoutId;
//    protected List<T> mDatas;
//    protected LayoutInflater mInflater;
//    protected Gson gson = new Gson();
//    private OnItemClickListener mOnItemClickListener;
//
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }
//
//    public XRecyclerAdapterJia2(Context context, int layoutId, List<T> datas) {
//        mContext = context;
//        mInflater = LayoutInflater.from(context);
//        mLayoutId = layoutId;
//        mDatas = datas;
//    }
//
//    @Override
//    public XRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
//        XRecyclerViewHolder viewHolder = XRecyclerViewHolder.get(mContext, null, parent, mLayoutId, -1);
//        setListener(parent, viewHolder, viewType);
//        return viewHolder;
//    }
//
//    protected int getPosition(XRecyclerViewHolder viewHolder) {
//        return viewHolder.getAdapterPosition();
//    }
//
//    protected boolean isEnabled(int viewType) {
//        return true;
//    }
//
//
//    protected void setListener(final ViewGroup parent, final XRecyclerViewHolder viewHolder, int viewType) {
//        if (!isEnabled(viewType)) return;
//        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    int position = getPosition(viewHolder);
//                    if (position >= 0 && position < mDatas.size())
//                        mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
//                }
//            }
//        });
//
//        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnItemClickListener != null) {
//                    int position = getPosition(viewHolder);
//                    if (position >= 0 && position < mDatas.size())
//                        return mOnItemClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
//                }
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public void onBindViewHolder(XRecyclerViewHolder holder, int position) {
//        holder.updatePosition(position);
//        convert(holder, mDatas.get(position), position);
//    }
//
//    public abstract void convert(XRecyclerViewHolder holder, T bean, int position);
//
//    @Override
//    public int getItemCount() {
//        return mDatas.size() != 0 ? mDatas.size() : 0;
//    }
//
//
//    public interface OnItemClickListener<T> {
//        void onItemClick(ViewGroup parent, View view, T bean, int position);
//
//        boolean onItemLongClick(ViewGroup parent, View view, T bean, int position);
//    }
//
//}