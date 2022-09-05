package com.hokaslibs.utils.recycler;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.hokaslibs.R;
import com.hokaslibs.utils.recycler.decoration.GridEntrust;
import com.hokaslibs.utils.recycler.decoration.SpacesItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


/**
 * @author: 蜡笔小新
 * @date: 2016-06-01 17:42
 * @GitHub: https://github.com/meikoz
 */
public class XRecyclerViewHelper {

    static XRecyclerViewHelper ourInstance;

    public XRecyclerViewHelper() {

    }

    public static XRecyclerViewHelper init() {
        if (ourInstance == null)
            ourInstance = new XRecyclerViewHelper();
        return ourInstance;
    }


    public void setLinearLayout(Context context, XRecyclerView mRecy) {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecy.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        //设置分隔线
//        mRecy.addItemDecoration(new SpacesItemDecoration(1));
        mRecy.setRefreshProgressStyle(ProgressStyle.BallTrianglePath);
        mRecy.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecy.setArrowImageView(R.drawable.abc_icon_down_arrow);
    }

    public void setLinearLayout(Context context, RecyclerView mRecy) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecy.setLayoutManager(layoutManager);
    }

    public void setLinearLayoutHORIZONTAL(Context context, RecyclerView mRecy) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecy.setLayoutManager(layoutManager);
    }

    public void setGridLayoutHORIZONTAL(Context context, RecyclerView mRecy) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecy.setLayoutManager(layoutManager);
    }

    public void setGridLayout(Context context, XRecyclerView mRecy) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        mRecy.setLayoutManager(layoutManager);
        mRecy.setNestedScrollingEnabled(false);
        //设置分隔线
//        mRecy.addItemDecoration(new SpacesItemDecoration(16));
        mRecy.setRefreshProgressStyle(ProgressStyle.BallTrianglePath);
        mRecy.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecy.setArrowImageView(R.drawable.abc_icon_down_arrow);
    }

    public void setStaggeredLayout(Context context, XRecyclerView mRecy) {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecy.setLayoutManager(layoutManager);
        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();
            }
        });
        //设置分隔线
//        mRecy.addItemDecoration(new SpacesItemDecoration(16));
        mRecy.setRefreshProgressStyle(ProgressStyle.BallTrianglePath);
        mRecy.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecy.setArrowImageView(R.drawable.abc_icon_down_arrow);
    }

}
