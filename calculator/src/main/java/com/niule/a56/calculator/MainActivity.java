package com.niule.a56.calculator;

import android.content.*;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.jpush.android.api.JPushInterface;
import com.niule.a56.calculator.base.XApplication;
import com.niule.a56.calculator.mvp.contract.VersionContract;
import com.niule.a56.calculator.mvp.presenter.VersionPresenter;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.mvp.ui.activity.FreightActivity;
import com.niule.a56.calculator.mvp.ui.activity.ResultActivity;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;
import com.niule.a56.calculator.utils.update.manager.AppVersion;
import com.niule.a56.calculator.utils.update.utils.DeviceUtils;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.bean.NotifyExtras;
import com.niule.a56.calculator.utils.AppBadgeUtil;
import com.niule.a56.calculator.utils.service.UpdateService;


public class MainActivity extends BaseActivity implements VersionContract.View, View.OnClickListener {

    private TextView tvStart;
    private AppVersion appVersion;

    private NotifyExtras notifyExtras;

    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_OPENED_ACTION = "MESSAGE_OPENED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        VersionPresenter versionPresenter = new VersionPresenter(this, this);

        notifyExtras = new NotifyExtras();

//        versionPresenter.getLastVersion();

        registerBroadcastReceiver();

        initView();
        initViews();

        setStatusBarPaddingWithPrimaryColor();
    }

    private void initViews() {
        tvTitle.setText(getString(R.string.app_name));
        tvTitleLeft.setVisibility(View.GONE);

        tvStart = findViewById(R.id.tvStart);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtil.removeOptions();
                intent2Activity(FreightActivity.class);
//                intent2Activity(ResultActivity.class);
            }
        });
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void showMessage(String message) {
        UiUtils.makeText(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onError(String errMsg) {

    }

    //通知进来的时候，会走这里
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (PreferencesUtil.getDataBoolean(com.hokaslibs.utils.Constant.LOGIN_EXCEPTION)) {
            showMessage("登录信息已失效，需重新登录");
            PreferencesUtil.removeDataString(com.hokaslibs.utils.Constant.LOGIN_EXCEPTION);
        } else {
            //MainActivity在后台时，通知被点击后，拉起MainActivity，处理推送
            notificationOpenedHandle(intent);
        }
    }

    private void notificationOpenedHandle(Intent intent) {
        String extra = intent.getStringExtra(KEY_EXTRAS);
        Log.d(TAG, "notificationOpenedHandle extra==" + extra);
        String notificationTitle = intent.getStringExtra(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        showNotifyDialog(extra,notificationTitle);
    }

    private void showNotifyDialog(String extra, String notificationTitle) {
        if (HokasUtils.isNoEmpty(extra)) {
            notifyExtras = gson.fromJson(extra, NotifyExtras.class);

            notifyExtras.setNotificationTitle(notificationTitle);

            if (!HokasUtils.isNoEmpty(notifyExtras.getNotifyCode())) {
                notifyExtras.setNotifyCode("");
            }

//            switch (notifyExtras.getNotifyCode()) {
//                case "401":
//                    Log.d(TAG, "异地登录消息到了");
//                    break;
//                case "601":
//                    Log.d(TAG, "收到生日通知！");
//                    birthdayBlessingDialog.show();
//                    birthdayBlessingDialog.gotoBirthday(notifyExtras);
//                    break;
//                case "501":
//                    redDialog.show();
//                    redDialog.gotoRedPack(notifyExtras);
//                    break;
//                case "":
//                    notifyDialog.show();
//                    switch (notifyExtras.getTitle()) {
//                        case "活信息审核失败":
//                        case "活信息审核通过":
//                            //活信息审核失败，兼容以前就版本后端没有发送notifyCode
//                            notifyDialog.gotoMyHuoList("去查看", notifyExtras);
//                            break;
//                        case "承接信息审核失败":
//                        case "承接信息审核通过":
//                            //承接审核失败，兼容以前就版本后端没有发送notifyCode
//                            notifyDialog.gotoMyCjList("去查看", notifyExtras);
//                            break;
//                        case "信息置顶即将过期":
//                        case "信息置顶已过期":
//                        case "信息置顶未支付":
//                            if (notifyExtras.getRelationId().equals("0")) {
//                                notifyDialog.gotoMyHuoList("去查看", notifyExtras);
//                            }
//                            if (notifyExtras.getRelationId().equals("1")) {
//                                notifyDialog.gotoMyCjList("去查看", notifyExtras);
//                            }
//                            break;
//                        case "跨境计算器赠送置顶券":
//                        case "跨境计算器赠送卡包":
//                            notifyDialog.gotoMyCouponList("去查看", notifyExtras);
//                            break;
//                        default:
//                            notifyDialog.gotoGeneralInfo(notifyExtras);
//                            break;
//                    }
//                    break;
//                case "201":
//                    notifyDialog.show();
//                    notifyDialog.gotoDetailsWork("去查看", notifyExtras);
//                    break;
//                case "202":
//                    notifyDialog.show();
//                    notifyDialog.gotoSubscription("去订阅", notifyExtras);  //去订阅通知, 暂时似乎没用  todo (展台通知还没有)
//                    break;
//                case "701":
//                    notifyDialog.show();
//                    notifyDialog.gotoTopStickPay("去查看", notifyExtras);   //置顶审核成功通知 (跳转到置顶支付页面)
//                    break;
//                case "702":  //置顶审核失败通知
//                    notifyDialog.show();
//                    if (HokasUtils.isNoEmpty(notifyExtras.getRelationId()) && notifyExtras.getRelationId().startsWith("00")) {
//                        notifyDialog.gotoMyHuoList("去查看", notifyExtras);
//                    }
//                    if (HokasUtils.isNoEmpty(notifyExtras.getRelationId()) && notifyExtras.getRelationId().startsWith("01")) {
//                        notifyDialog.gotoMyCjList("去查看", notifyExtras);
//                    }
//                    break;
////                case "703":   //置顶券赠送, 没有用这个，走的普通通知
////                case "704":   //置顶信息即将过期
////                notifyDialog.show();
////                    notifyDialog.gotoGeneralInfo(notifyExtras);
////                    break;
//                case "800":     //报价详情
//                    notifyDialog.show();
//                    notifyDialog.gotoMyHuo("去查看", notifyExtras);
//                    break;
//                case "900":     //外发活交易通知
//                    notifyDialog.show();
//                    notifyDialog.gotoTransactionDetails("去查看", notifyExtras);
//                    break;
//                default:
//                    notifyDialog.show();
//                    if (notifyExtras.getNotifyCode().length() == 4 && notifyExtras.getNotifyCode().startsWith("19")) {  //商品交易类通知
//                        notifyDialog.gotoMallTransactionDetails("去查看", notifyExtras);
//                    } else if (notifyExtras.getNotifyCode().length() == 4 && notifyExtras.getNotifyCode().startsWith("16")) {  //商品类通知
//                        notifyDialog.gotoMallCommodityDetails("去查看", notifyExtras);
//                    }
//                    break;
//            }
        }
    }

    private boolean isExceptionDialogShow = false;
    private android.app.AlertDialog.Builder exceptionBuilder;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        AppBadgeUtil.clearNum();        //清除角标 桌面图标角标

    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
        }
        unregisterBroadcastReceiver();
    }

    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_RECEIVED_ACTION);  //极光通知收到，不是点击打开通知
        intentFilter.addAction(MESSAGE_OPENED_ACTION);  //通知栏手动点击打开通知
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (HokasUtils.isNoEmpty(intent.getAction())) {
                    switch (intent.getAction()) {
//                        case Constant.ACTION_CONTACT_CHANAGED:
//                        case Constant.ACTION_GROUP_CHANAGED:
//                            updateUnreadLabel();
//                            if (currentTabIndex == 3) {  //聊天页面
//                                // refresh conversation list
//                                if (homeFourFragment != null) {
//                                    UiUtils.makeText("刷新");
////                                    homeFourFragment.refresh();
//                                }
//                            }
//                            break;
                        case MESSAGE_RECEIVED_ACTION:
                            jgNotifyArrivedHandle(intent);
                            break;
                        case MESSAGE_OPENED_ACTION:
                            notificationOpenedHandle(intent);
                            break;
                    }
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void jgNotifyArrivedHandle(Intent intent) {
        //获取extra信息
        String extra = intent.getStringExtra(KEY_EXTRAS);
        Log.d(TAG, "jgNotifyArrivedHandle extra==" + extra);

        if (HokasUtils.isNoEmpty(extra)) {
            notifyExtras = gson.fromJson(extra, NotifyExtras.class);
            if (notifyExtras.getNotifyCode().equals("201")) {  //订阅消息，播放声音
                playSound(XApplication.currentActivity());
            } else {
                //通知悬浮横幅，是在手机端用户自己设置的，这里等用户手动点击横幅，再打开相应的页面
//                    showNotifyDialog(extra, intent.getStringExtra(JPushInterface.EXTRA_NOTIFICATION_TITLE));
            }
        }
    }

    private static Ringtone mRingtone;

    public synchronized void playSound(Context context) {
        if (mRingtone == null) {
            String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.subscription;
            Uri no = Uri.parse(uri);
            mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), no);
        }
        if (!mRingtone.isPlaying()) {
            mRingtone.play();
        }
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    public boolean isConflict = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }



    @Override
    public void onVersion(AppVersion bean) {
        if (bean != null) {
            appVersion = bean;
            if (Integer.parseInt(bean.getVersionCode()) > DeviceUtils.getVersionCode(this)) {
                new UpdateService(this).checkUpdate(bean);
            }
        }
    }

    //安装权限
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            if (resultCode == RESULT_OK) {
                if (appVersion != null) {
                    new UpdateService(this).checkUpdate(appVersion);
                }
            } else {
                finish();
            }
        }
    }

    private Long lastTime = 0L;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime < 2 * 1000) {
            finish();
        } else {
            UiUtils.makeText("再按一次将退出跨境计算器");
            lastTime = currentTime;
        }
    }

}
