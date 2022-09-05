package com.niule.a56.calculator.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.google.gson.Gson;
import com.hokaslibs.R;
import com.hokaslibs.utils.Loading;
import com.hokaslibs.utils.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.io.Serializable;

public abstract class XFragment extends RxFragment {
    protected View mRootView;

    public TextView tvTitleLeft;
    public LinearLayout ll_left_box;
    public LinearLayout ll_right_btn_box;
    public TextView tvTitle;
    public TextView tvBtn;
    public ImageView ivBtn;
    public RelativeLayout appbar;

    private Dialog progressDialog;

    protected Gson gson;
    public int PAGE = 1;
    public int SIZE = 10;
    public BaseAnimatorSet mBasIn;
    public BaseAnimatorSet mBasOut;

    protected InputMethodManager inputMethodManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mRootView == null)
            mRootView = inflater.inflate(getLayoutResource(), container, false);
        progressDialog = Loading.createLoadingDialog(getActivity(), "请稍等...", true,
                0);
        gson = new Gson();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        onInitView();
        return mRootView;
    }

    protected int getLayoutResource(){ return 0;};

    protected void onInitView(){};

    public void showLoadingView() {
        if (progressDialog != null)
            progressDialog.show();
    }

    public void hideLoadingView() {
        if (progressDialog != null)
            progressDialog.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideLoadingView();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
//        if (pDialog != null) {
//            pDialog.dismiss();
//            pDialog = null;
//        }
        super.onDestroyView();
    }

    public void initView() {
        tvTitleLeft = mRootView.findViewById(R.id.tvTitleLeft);
        ll_left_box = mRootView.findViewById(R.id.ll_left_box);
        tvTitle = mRootView.findViewById(R.id.tvTitle);
        tvBtn = mRootView.findViewById(R.id.tvBtn);
        ivBtn = mRootView.findViewById(R.id.ivBtn);
        appbar = mRootView.findViewById(R.id.appBar);
//        tvTitleLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
        ll_left_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    public void setIvBtnImg(int resId) {
        ivBtn.setImageResource(resId);
    }

    public ImageView getIvBtn() {
        return ivBtn;
    }


    protected void backStack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    protected void back() {
        tvTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backStack();
            }
        });
    }

    protected void setTvTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    protected void setTvTitle(int title) {
        tvTitle.setText(title);
    }

    protected void setTvTitleLeft(String titleLeft) {
        tvTitleLeft.setText(titleLeft);
    }

    protected void setTvBtn(String titleRight) {
        tvBtn.setText(titleRight);
    }

    protected void setStatusBarPaddingWithPrimaryColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mRootView.findViewById(R.id.bar).setPadding(0, StatusBarUtil.getStatusBarHeight(getActivity()), 0, 0);
            mRootView.findViewById(R.id.bar).setBackgroundResource(R.drawable.sp_jb_bar_bg);
        }
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(getContext(), tarActivity);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, Object object, int index, int type) {
        Intent intent = new Intent(getContext(), tarActivity);
        intent.putExtra("index", index);
        intent.putExtra("type", type);
        intent.putExtra("bean", (Serializable) object);
        startActivity(intent);
    }
    protected void intent2Activity(Class<? extends Activity> tarActivity, int index) {
        Intent intent = new Intent(getContext(), tarActivity);
        intent.putExtra("index", index);
        startActivity(intent);

    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, int index, Object object) {
        Intent intent = new Intent(getContext(), tarActivity);
        intent.putExtra("index", index);
        intent.putExtra("bean", (Serializable) object);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, Object object) {
        Intent intent = new Intent(getContext(), tarActivity);
        intent.putExtra("bean", (Serializable) object);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, Object object, int type) {
        Intent intent = new Intent(getContext(), tarActivity);
        intent.putExtra("bean", (Serializable) object);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    protected void intent2ActivityOnResult(Class<? extends Activity> tarActivity, int requestCode) {
        Intent intent = new Intent(getActivity(), tarActivity);
        startActivityForResult(intent, requestCode);
    }

    protected void intent2ActivityOnResult(Class<? extends Activity> tarActivity, int requestCode, int index) {
        Intent intent = new Intent(getActivity(), tarActivity);
        intent.putExtra("index", index);
        startActivityForResult(intent, requestCode);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, Object object, Object list, int code) {
        Intent intent = new Intent(getContext(), tarActivity);
        intent.putExtra("bean", (Serializable) object);
        intent.putExtra("list", (Serializable) list);
        startActivityForResult(intent, code);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, Object object, int code) {
        Intent intent = new Intent(getContext(), tarActivity);
        intent.putExtra("bean", (Serializable) object);
        startActivityForResult(intent, code);
    }
    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
