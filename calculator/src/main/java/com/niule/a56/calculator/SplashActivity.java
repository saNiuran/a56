package com.niule.a56.calculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.*;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;
import com.hokaslibs.utils.*;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.mvp.ui.activity.HtmlActivity;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.dialog.AlertWebViewDialog;
import com.niule.a56.calculator.utils.service.UpdateService;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import static com.niule.a56.calculator.jpush.TagAliasOperatorHelper.sequence;


public class SplashActivity extends BaseActivity {

    private WebView webView;
    private TextView tvSkip;
    private RelativeLayout rlAdvertisement;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onInitView() {
        initViews();

        //非第一次使用
        if (PreferencesUtil.getDataBoolean("userAgreement")) {
            JPushInterface.deleteTags(this, sequence++, null);
            JPushInterface.deleteAlias(this, sequence++);
        }

        ZQImageViewRoundOval iv = findViewById(R.id.ivIcon);
        iv.setRoundRadius(25);

        //删除更新文件夹
        new UpdateService(this).deleteFile();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!PreferencesUtil.getDataBoolean("userAgreement")) {
            showUserAgreement();
        } else {
            mHandler.sendEmptyMessageDelayed(3,2000);
        }
    }

    private void showUserAgreement() {
        //让用户确认协议
        HandlerTip.getInstance().postDelayed(100, () -> {
            final AlertWebViewDialog dialog = new AlertWebViewDialog(SplashActivity.this, mHandler, "https://h5.56.5niule.com/docs/agreeTip.html").builder();
            dialog.setTitle("服务协议和隐私政策")
                    .setPositiveButton("同意", v -> {
                        PreferencesUtil.setDataBoolean("userAgreement", true);

                        dialog.dismiss();

//                      友盟
                        PreferencesUtil.setDataString("uminit", "1");
                        UMConfigure.submitPolicyGrantResult(getApplication(), true);
                        UMConfigure.init(getApplication(), "59b62dd0ae1bf84243000069"
                                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
                        UMShareAPI.get(getApplication());
                        PlatformConfig.setWeixin("wx748a108c33b58742", "fed9a520043bf76e660bf19fb20ef796");
                        PlatformConfig.setQQZone("1106537132", "9fkFBghMQ3Ku56ex");

                        JPushInterface.init(getApplicationContext());            // 初始化 JPush
                        JAnalyticsInterface.init(this);         //初始化统计

                        mHandler.sendEmptyMessageDelayed(3, 1000);
                    })
                    .setNegativeButton("不同意并退出APP", v -> finish()).show();
        });

    }

    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(SplashActivity.this, HtmlActivity.class);
            switch (msg.what) {
                case 1:
                    intent.putExtra("title", "服务协议");
                    intent.putExtra("html", "agreement/index.html");
                    startActivity(intent);
                    break;
                case 2:
                    intent.putExtra("title", "隐私政策");
                    intent.putExtra("html", "privacy/index.html");
                    startActivity(intent);
                    break;
                case 3:
                    intent2Activity(MainActivity.class);
                    finish();
                    break;
            }
        }
    };

    private void initViews() {
        webView = findViewById(R.id.webView);
        tvSkip = findViewById(R.id.tvSkip);
        rlAdvertisement = findViewById(R.id.rlAdvertisement);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
