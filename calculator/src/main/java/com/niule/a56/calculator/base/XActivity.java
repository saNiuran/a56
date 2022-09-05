package com.niule.a56.calculator.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.google.gson.Gson;
import com.hokaslibs.R;
import com.hokaslibs.utils.Loading;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.hokaslibs.utils.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;


/**
 * 作者： Hokas
 * 时间： 2017/1/3
 * 类别：
 */

public abstract class XActivity extends RxAppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    public static final String ACTION_RECEIVER_ACTIVITY = "com.hokaslibs";
    public TextView tvTitleLeft;
    public LinearLayout ll_left_box;
    public LinearLayout ll_right_btn_box;
    public TextView tvTitle;
    public TextView tvBtn;
    public ImageView ivBtn;
    public RelativeLayout appbar;

    private Dialog progressDialog;
    //    private SweetAlertDialog pDialog;
    protected Gson gson;

    protected BaseAnimatorSet mBasIn;
    protected BaseAnimatorSet mBasOut;

    protected int PAGE = 1;
    protected int SIZE = 10;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutResource());

        progressDialog = Loading.createLoadingDialog(this, "请稍等...", true,
                0);

        XApplication.addActivity(this);
        gson = new Gson();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        hideLoadingView();

        onInitView();
    }

    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    public void showLoadingView() {
        progressDialog.show();
    }

    public void hideLoadingView() {
        progressDialog.hide();
    }


    public void initView() {
        tvTitleLeft = findViewById(R.id.tvTitleLeft);
        ll_left_box = findViewById(R.id.ll_left_box);
        ll_right_btn_box = findViewById(R.id.ll_right_btn_box);
        tvTitle = findViewById(R.id.tvTitle);
        tvBtn = findViewById(R.id.tvBtn);
        ivBtn = findViewById(R.id.ivBtn);
        appbar = findViewById(R.id.appBar);
        tvTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                hideSoftInput(v);
            }
        });
        ll_left_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                hideSoftInput(v);
            }
        });
    }

    protected void setTvTitle(String title) {
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

    /**
     * 通过intent返回对象
     */
    protected void intentReturn(Object object) {
        Intent intent = new Intent();
        intent.putExtra("bean", (Serializable) object);
        setResult(RESULT_OK, intent);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, int code) {
        Intent intent = new Intent(this, tarActivity);
        startActivityForResult(intent, code);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, int index, int code) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", index);
        startActivityForResult(intent, code);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, int index, int type, int code) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", index);
        intent.putExtra("type", type);
        startActivityForResult(intent, code);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, Object object, Object list, int code) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("bean", (Serializable) object);
        intent.putExtra("list", (Serializable) list);
        startActivityForResult(intent, code);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, int type, int code, Object object) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("type", type);
        intent.putExtra("bean", (Serializable) object);
        startActivityForResult(intent, code);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, Object object, int code) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("bean", (Serializable) object);
        startActivityForResult(intent, code);
    }

    protected void intent2ActivityReturn(Class<? extends Activity> tarActivity, Object object, int index, int code) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("bean", (Serializable) object);
        intent.putExtra("index", index);
        startActivityForResult(intent, code);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, int index) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, long index) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, int index, int type) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", index);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, Object index, int type) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", (Serializable) index);
        intent.putExtra("type", type);
        startActivity(intent);
    }


    protected void intent2Activity(Class<? extends Activity> tarActivity, Object object, int index, int type) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", index);
        intent.putExtra("type", type);
        intent.putExtra("bean", (Serializable) object);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, Object object) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("bean", (Serializable) object);
        startActivity(intent);
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity, int index, Object object) {
        Intent intent = new Intent(this, tarActivity);
        intent.putExtra("index", index);
        intent.putExtra("bean", (Serializable) object);
        startActivity(intent);
    }


    public void setStatusBarMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else
            StatusBarUtil.setTranslucentStatus(this, true);
    }

    /**
     * 设置全屏模式透明
     */
    public void setStatusBarMode2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else
            StatusBarUtil.setTranslucentStatus(this, true);
    }

    public void setStatusBarMode3() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else
            StatusBarUtil.setTranslucentStatus(this, true);
    }

    public void setStatusBarPaddingWithPrimaryColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.bar).setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
            findViewById(R.id.bar).setBackgroundResource(R.drawable.sp_jb_bar_bg);
        }
    }

    @SuppressLint("ResourceType")
    public void setStatusBarPaddingWithClearBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.bar).setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
            findViewById(R.id.bar).setBackgroundResource(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferencesUtil.getDataBoolean("userAgreement")) {
            MobclickAgent.onResume(this);
        }
    }


    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        if (PreferencesUtil.getDataBoolean("userAgreement")) {
            MobclickAgent.onPause(this);
        }
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        XApplication.finishSingleActivity(this);
        super.onDestroy();
    }

    /**
     * 强制隐藏键盘
     */
    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 如果输入法在窗口上已经显示，则隐藏，反之则显示
     */
    public void softInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 调用隐藏系统默认的输入法
     */
    public void hideSoftInputFromWindow() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken()
                        , InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取输入法打开的状态
     */
    public boolean softInputIsActive() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {//点击editText控件外部
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    hideSoftInput(v);
                    if (editText != null) {
                        editText.clearFocus();
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    EditText editText = null;

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            editText = (EditText) v;
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
