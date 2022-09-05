//package com.hokaslibs.utils.screening;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Build;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.baiiu.filter.interfaces.OnFilterDoneListener;
//import com.hokaslibs.R;
//
//import java.util.List;
//
//
//
///**
// * auther: baiiu
// * time: 16/6/5 05 23:03
// * description:
// */
//public class BetterDoubleGridView extends LinearLayout implements View.OnClickListener {
//
//    RecyclerView recyclerView;
//
//    private List<String> mTopGridData;
//    private List<String> mBottomGridList;
//    private List<String> mJiaoQiGridList;
//    private OnFilterDoneListener mOnFilterDoneListener;
//
//
//    public BetterDoubleGridView(Context context) {
//        this(context, null);
//    }
//
//    public BetterDoubleGridView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr,
//                                int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
//    }
//
//    private void init(Context context) {
//        setBackgroundColor(Color.WHITE);
//        View view = inflate(context, R.layout.merge_filter_double_grid, this);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//    }
//
//
//    public BetterDoubleGridView setmTopGridData(List<String> mTopGridData) {
//        this.mTopGridData = mTopGridData;
//        return this;
//    }
//
//    public BetterDoubleGridView setmBottomGridList(List<String> mBottomGridList) {
//        this.mBottomGridList = mBottomGridList;
//        return this;
//    }
//
//    public BetterDoubleGridView setmJiaoQiGridList(List<String> mJiaoQiGridList) {
//        this.mJiaoQiGridList = mJiaoQiGridList;
//        return this;
//    }
//
//    public BetterDoubleGridView build() {
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position == 0 || position == mTopGridData.size() + 1 || position == mTopGridData.size() + mBottomGridList.size() + 2) {
//                    return 4;
//                }
//                return 1;
//            }
//        });
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(new DoubleGridAdapter(getContext(), mTopGridData, mBottomGridList
//                , mJiaoQiGridList, this));
//
//        return this;
//    }
//
//    public void setAapterS(){
//        recyclerView.setAdapter(new DoubleGridAdapter(getContext(), mTopGridData, mBottomGridList
//                , mJiaoQiGridList, this));
//    }
//
//
//    private TextView mTopSelectedTextView;
//    private TextView mBottomSelectedTextView;
//    private TextView mJiaoQiSelectedTextView;
//
//    @Override
//    public void onClick(View v) {
//
//        TextView textView = (TextView) v;
//        String text = (String) textView.getTag();
//        if (textView == mTopSelectedTextView) {
//            mTopSelectedTextView = null;
//            textView.setSelected(false);
//        } else if (textView == mBottomSelectedTextView) {
//            mBottomSelectedTextView = null;
//            textView.setSelected(false);
//        } else if (textView == mJiaoQiSelectedTextView) {
//            mJiaoQiSelectedTextView = null;
//            textView.setSelected(false);
//        } else if (mTopGridData.contains(text)) {
//            if (mTopSelectedTextView != null) {
//                mTopSelectedTextView.setSelected(false);
//            }
//            mTopSelectedTextView = textView;
//            textView.setSelected(true);
//        } else if (mBottomGridList.contains(text)) {
//            if (mBottomSelectedTextView != null) {
//                mBottomSelectedTextView.setSelected(false);
//            }
//            mBottomSelectedTextView = textView;
//            textView.setSelected(true);
//        } else if (mJiaoQiGridList.contains(text)) {
//            if (mJiaoQiSelectedTextView != null) {
//                mJiaoQiSelectedTextView.setSelected(false);
//            }
//            mJiaoQiSelectedTextView = textView;
//            textView.setSelected(true);
//        }
//    }
//
//
//    public BetterDoubleGridView setOnFilterDoneListener(OnFilterDoneListener listener) {
//        mOnFilterDoneListener = listener;
//        return this;
//    }
//
//    public void clickDone(View view) {
//
//    }
//
//
//}
