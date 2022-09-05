package com.niule.a56.calculator.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.base.BaseActivity;

public class HtmlActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_html;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onInitView() {
        initView();
        initViews();

        setStatusBarPaddingWithPrimaryColor();

        setTvTitle(getIntent().getStringExtra("title"));
        ll_left_box.setOnClickListener(this);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setTextZoom(100);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowUniversalAccessFromFileURLs(false);

        HokasUtils.logcat("https://h5.56.5niule.com/docs/" + getIntent().getStringExtra("html"));
        webView.loadUrl("https://h5.56.5niule.com/docs/" + getIntent().getStringExtra("html"));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.webView);

    }

    @Override
    public void onClick(View v) {
        if (webView.canGoBack())
            webView.goBack();
        else
            finish();
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
