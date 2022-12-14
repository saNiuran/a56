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
import com.niule.a56.calculator.mvp.ui.activity.EnquiryActivity;
import com.niule.a56.calculator.mvp.ui.activity.FreightActivity;
import com.niule.a56.calculator.mvp.ui.activity.SalesLeadActivity;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;
import com.niule.a56.calculator.utils.update.manager.ApkVersion;
import com.niule.a56.calculator.utils.update.utils.DeviceUtils;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.bean.NotifyExtras;
import com.niule.a56.calculator.utils.AppBadgeUtil;
import com.niule.a56.calculator.utils.service.UpdateService;


public class MainActivity extends BaseActivity implements VersionContract.View, View.OnClickListener {

    private TextView tvStart;
    private ApkVersion apkVersion;

    private NotifyExtras notifyExtras;

    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "MESSAGE_RECEIVED_ACTION";
    public static final String MESSAGE_OPENED_ACTION = "MESSAGE_OPENED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        VersionPresenter versionPresenter = new VersionPresenter(this, this);

        notifyExtras = new NotifyExtras();

        versionPresenter.getLastVersion();

        registerBroadcastReceiver();

        initView();
        initViews();

        setStatusBarPaddingWithPrimaryColor();
    }

    private void initViews() {
        tvTitle.setText(getString(R.string.app_name));
        tvTitleLeft.setVisibility(View.GONE);

        tvStart = findViewById(R.id.tvStart);
        tvStart.setOnClickListener(v -> {
            PreferencesUtil.removeOptions();
            intent2Activity(FreightActivity.class);
//                intent2Activity(ResultActivity.class);
//                intent2Activity(SalesLeadActivity.class);
//                intent2Activity(EnquiryActivity.class);
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

    //????????????????????????????????????
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (PreferencesUtil.getDataBoolean(com.hokaslibs.utils.Constant.LOGIN_EXCEPTION)) {
            showMessage("???????????????????????????????????????");
            PreferencesUtil.removeDataString(com.hokaslibs.utils.Constant.LOGIN_EXCEPTION);
        } else {
            //MainActivity??????????????????????????????????????????MainActivity???????????????
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
//                    Log.d(TAG, "????????????????????????");
//                    break;
//                case "601":
//                    Log.d(TAG, "?????????????????????");
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
//                        case "?????????????????????":
//                        case "?????????????????????":
//                            //???????????????????????????????????????????????????????????????notifyCode
//                            notifyDialog.gotoMyHuoList("?????????", notifyExtras);
//                            break;
//                        case "????????????????????????":
//                        case "????????????????????????":
//                            //????????????????????????????????????????????????????????????notifyCode
//                            notifyDialog.gotoMyCjList("?????????", notifyExtras);
//                            break;
//                        case "????????????????????????":
//                        case "?????????????????????":
//                        case "?????????????????????":
//                            if (notifyExtras.getRelationId().equals("0")) {
//                                notifyDialog.gotoMyHuoList("?????????", notifyExtras);
//                            }
//                            if (notifyExtras.getRelationId().equals("1")) {
//                                notifyDialog.gotoMyCjList("?????????", notifyExtras);
//                            }
//                            break;
//                        case "??????????????????????????????":
//                        case "???????????????????????????":
//                            notifyDialog.gotoMyCouponList("?????????", notifyExtras);
//                            break;
//                        default:
//                            notifyDialog.gotoGeneralInfo(notifyExtras);
//                            break;
//                    }
//                    break;
//                case "201":
//                    notifyDialog.show();
//                    notifyDialog.gotoDetailsWork("?????????", notifyExtras);
//                    break;
//                case "202":
//                    notifyDialog.show();
//                    notifyDialog.gotoSubscription("?????????", notifyExtras);  //???????????????, ??????????????????  todo (?????????????????????)
//                    break;
//                case "701":
//                    notifyDialog.show();
//                    notifyDialog.gotoTopStickPay("?????????", notifyExtras);   //???????????????????????? (???????????????????????????)
//                    break;
//                case "702":  //????????????????????????
//                    notifyDialog.show();
//                    if (HokasUtils.isNoEmpty(notifyExtras.getRelationId()) && notifyExtras.getRelationId().startsWith("00")) {
//                        notifyDialog.gotoMyHuoList("?????????", notifyExtras);
//                    }
//                    if (HokasUtils.isNoEmpty(notifyExtras.getRelationId()) && notifyExtras.getRelationId().startsWith("01")) {
//                        notifyDialog.gotoMyCjList("?????????", notifyExtras);
//                    }
//                    break;
////                case "703":   //???????????????, ????????????????????????????????????
////                case "704":   //????????????????????????
////                notifyDialog.show();
////                    notifyDialog.gotoGeneralInfo(notifyExtras);
////                    break;
//                case "800":     //????????????
//                    notifyDialog.show();
//                    notifyDialog.gotoMyHuo("?????????", notifyExtras);
//                    break;
//                case "900":     //?????????????????????
//                    notifyDialog.show();
//                    notifyDialog.gotoTransactionDetails("?????????", notifyExtras);
//                    break;
//                default:
//                    notifyDialog.show();
//                    if (notifyExtras.getNotifyCode().length() == 4 && notifyExtras.getNotifyCode().startsWith("19")) {  //?????????????????????
//                        notifyDialog.gotoMallTransactionDetails("?????????", notifyExtras);
//                    } else if (notifyExtras.getNotifyCode().length() == 4 && notifyExtras.getNotifyCode().startsWith("16")) {  //???????????????
//                        notifyDialog.gotoMallCommodityDetails("?????????", notifyExtras);
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
        AppBadgeUtil.clearNum();        //???????????? ??????????????????

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
        intentFilter.addAction(MESSAGE_RECEIVED_ACTION);  //?????????????????????????????????????????????
        intentFilter.addAction(MESSAGE_OPENED_ACTION);  //?????????????????????????????????
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (HokasUtils.isNoEmpty(intent.getAction())) {
                    switch (intent.getAction()) {
//                        case Constant.ACTION_CONTACT_CHANAGED:
//                        case Constant.ACTION_GROUP_CHANAGED:
//                            updateUnreadLabel();
//                            if (currentTabIndex == 3) {  //????????????
//                                // refresh conversation list
//                                if (homeFourFragment != null) {
//                                    UiUtils.makeText("??????");
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
        //??????extra??????
        String extra = intent.getStringExtra(KEY_EXTRAS);
        Log.d(TAG, "jgNotifyArrivedHandle extra==" + extra);

        if (HokasUtils.isNoEmpty(extra)) {
            notifyExtras = gson.fromJson(extra, NotifyExtras.class);
            if (notifyExtras.getNotifyCode().equals("201")) {  //???????????????????????????
                playSound(XApplication.currentActivity());
            } else {
                //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
    public void onVersion(ApkVersion bean) {
        if (bean != null) {
            apkVersion = bean;
            if (Integer.parseInt(bean.getVersionCode()) > DeviceUtils.getVersionCode(this)) {
                new UpdateService(this).checkUpdate(bean);
            }
        }
    }

    //????????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            if (resultCode == RESULT_OK) {
                if (apkVersion != null) {
                    new UpdateService(this).checkUpdate(apkVersion);
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
            UiUtils.makeText("????????????????????????????????????");
            lastTime = currentTime;
        }
    }

}
