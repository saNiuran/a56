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
//public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<XRecyclerViewHolder> {
//    protected Context mContext;
//    protected int mLayoutId;
//    protected List<T> mDatas;
//    protected LayoutInflater mInflater;
//    protected Gson gson = new Gson();
//    private OnItemClickListener mOnItemClickListener;
//
//    private XRecyclerViewHolder viewHolder;
//
//    public XRecyclerViewHolder getViewHolder() {
//        return viewHolder;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }
//
//    public RecyclerAdapter(Context context, int layoutId, List<T> datas) {
//        mContext = context;
//        mInflater = LayoutInflater.from(context);
//        mLayoutId = layoutId;
//        mDatas = datas;
//    }
//
//    @Override
//    public XRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
//        int itemCount = getItemCount();
//        viewHolder = XRecyclerViewHolder.get(mContext, null, parent, mLayoutId, itemCount);
//        setListener(parent, viewHolder, viewType);
//        return viewHolder;
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
//                    int position = viewHolder.getXPosition();
//                    if (mDatas.size() > 0)
//                        mOnItemClickListener.onItemClick(parent, v, mDatas.get(position ), position );
//                }
//            }
//        });
//
//        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnItemClickListener != null) {
//                    int position = viewHolder.getXPosition();
//                    if (mDatas.size() > 0)
//                        return mOnItemClickListener.onItemLongClick(parent, v, mDatas.get(position ), position );
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