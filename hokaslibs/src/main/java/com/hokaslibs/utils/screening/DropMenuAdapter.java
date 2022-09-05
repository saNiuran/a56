//package com.hokaslibs.utils.screening;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.FrameLayout;
//
//import com.baiiu.filter.adapter.MenuAdapter;
//import com.baiiu.filter.interfaces.OnFilterDoneListener;
//import com.baiiu.filter.interfaces.OnFilterItemClickListener;
//import com.baiiu.filter.typeview.SingleGridView;
//import com.baiiu.filter.typeview.SingleListView;
//import com.baiiu.filter.util.UIUtil;
//import com.baiiu.filter.view.FilterCheckedTextView;
//import com.hokaslibs.R;
//import com.hokaslibs.mvp.bean.Industry;
//import com.hokaslibs.mvp.bean.SortSign;
//import com.hokaslibs.utils.screening.holder.SimpleText2Adapter;
//
//import java.util.List;
//
///**
// * author: baiiu
// * date: on 16/1/17 21:14
// * description:
// */
//public class DropMenuAdapter implements MenuAdapter {
//    private final Context mContext;
//    private OnFilterDoneListener onFilterDoneListener;
//    private String[] titles;
//    private List<Industry> industryList;
//    private List<SortSign> sortSignList;
//
//    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener,
//                           List<Industry> industryList, List<SortSign> sortSignList) {
//        this.mContext = context;
//        this.titles = titles;
//        this.onFilterDoneListener = onFilterDoneListener;
//        this.industryList = industryList;
//        this.sortSignList = sortSignList;
//    }
//
//    @Override
//    public int getMenuCount() {
//        return titles.length;
//    }
//
//    @Override
//    public String getMenuTitle(int position) {
//        return titles[position];
//    }
//
//    @Override
//    public int getBottomMargin(int position) {
//        if (position == 2) {
//            return 0;
//        }
//
//        return UIUtil.dp2px(mContext, 100);
//    }
//
//    @Override
//    public View getView(int position, FrameLayout parentContainer) {
//        View view = parentContainer.getChildAt(position);
//
//        switch (position) {
//            case 0:
//                view = createDoubleListView();
//                break;
//            case 1:
//                view = createSingleListView();
//                break;
////            case 2:
////                view = createBetterDoubleGrid();
////                break;
//        }
//
//        return view;
//    }
//
//    private View createSingleListView() {
//        final SingleListView<SortSign> singleListView = new SingleListView<>(mContext);
//        singleListView.adapter(new SimpleText2Adapter<SortSign>(null, mContext) {
//            @Override
//            public String provideText(SortSign string) {
//                return string.getContent();
//            }
//
//            @Override
//            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
////                        int dp = UIUtil.dp(mContext, 15);
////                        checkedTextView.setPadding(dp, dp, 0, dp);
////                        checkedTextView.setChecked(true);
//            }
//        });
//        singleListView.onItemClick(new OnFilterItemClickListener<SortSign>() {
//            @Override
//            public void onItemClick(SortSign item) {
//                if (onFilterDoneListener != null) {
//                    for (SortSign bean : sortSignList) {
//                        bean.setFlag(false);
//                    }
//                    item.setFlag(true);
//                    singleListView.setList(sortSignList, 0);
//                    onFilterDoneListener.onTwo(1, item.getContent(), item.getSortSign());
//                }
//            }
//        });
//
//        singleListView.setList(sortSignList, 0);
//
//        return singleListView;
//    }
//
//
//    private View createDoubleListView() {
//        Industry allBean = new Industry();
//        allBean.setIndustry("全部");
//        allBean.setIndustryIcon(R.mipmap.ic_hy_all);
//        industryList.add(0, allBean);
//        SingleGridView<Industry> singleListView = new SingleGridView<Industry>(mContext)
//                .adapter(new NationwideAdapter<Industry>(null, mContext) {
//                    @Override
//                    public String provideText(Industry bean) {
//                        return bean.getIndustry();
//                    }
//
//                    @Override
//                    public String provideIcon(Industry bean) {
//                        return bean.getIcon();
//                    }
//
//                    @Override
//                    public int provideIconInt(Industry bean) {
//                        return bean.getIndustryIcon();
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
////                        int dp = UIUtil.dp(mContext, 15);
////                        checkedTextView.setPadding(dp, dp, 0, dp);
//                    }
//                })
//                .onItemClick(new OnFilterItemClickListener<Industry>() {
//                    @Override
//                    public void onItemClick(Industry item) {
//                        if (onFilterDoneListener != null) {
//                            onFilterDoneListener.onOne(0, item.getIndustry());
//                        }
//                    }
//                });
//        singleListView.setList(industryList, 0);
//
//        return singleListView;
//    }
//
//
//}
