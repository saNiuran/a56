package com.niule.a56.calculator.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.niule.a56.calculator.MainActivity;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.SplashActivity;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.dialog.Hint2Dialog;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;


public abstract class BaseActivity extends XActivity implements BGASwipeBackHelper.Delegate {

    protected InputMethodManager inputMethodManager;

    protected BGASwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!(this instanceof MainActivity || this instanceof SplashActivity)) {
            initSwipeBackFinish();
        }


        setStatusBarMode();
        //这里面要区分，哪些是特殊的导航条，需要改成一定格式的，必须是 id/bar 里面套一层 id/appBar

        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }


        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyDialogShow();
        if (PreferencesUtil.getDataBoolean("userAgreement")) {
            JAnalyticsInterface.onPageStart(this, getClass().getName());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (PreferencesUtil.getDataBoolean("userAgreement")) {
            JAnalyticsInterface.onPageEnd(this, getClass().getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * back
     */
    public void back(View view) {
        finish();
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
//        if (mSwipeBackHelper != null) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
//        }
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        if (mSwipeBackHelper != null)
            mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper != null) {
            if (mSwipeBackHelper.isSliding()) {
                return;
            }
            mSwipeBackHelper.backward();
        }
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    //通知相关dialog
    private void notifyDialogShow() {
        showNormalNotify();
    }


    private void showNormalNotify() {
        if (PreferencesUtil.getDataBoolean(com.hokaslibs.utils.Constant.普通通知)) {
            Hint2Dialog dialog = new Hint2Dialog(this);
            dialog.show();
            dialog.setTitle(PreferencesUtil.getDataString(com.hokaslibs.utils.Constant.普通通知标题));
            dialog.setContent(PreferencesUtil.getDataString(com.hokaslibs.utils.Constant.普通通知内容));
            dialog.toTestDismiss();
            PreferencesUtil.removeDataString(com.hokaslibs.utils.Constant.普通通知);
            PreferencesUtil.removeDataString(com.hokaslibs.utils.Constant.普通通知内容);
            PreferencesUtil.removeDataString(com.hokaslibs.utils.Constant.普通通知标题);
        }
    }



}
