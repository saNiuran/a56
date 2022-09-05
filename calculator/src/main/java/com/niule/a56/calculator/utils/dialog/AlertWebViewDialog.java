package com.niule.a56.calculator.utils.dialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextPaint;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import androidx.core.content.ContextCompat;
import com.hokaslibs.R;
import com.hokaslibs.utils.HandlerTip;

@SuppressWarnings("Duplicates")
public class AlertWebViewDialog {
    private Activity activity;
    private Dialog dialog;
    private Handler mHandler;
    private LinearLayout lLayout_bg;
    private LinearLayout ll_line_1;
    private LinearLayout ll_line_2;
    private LinearLayout ll_webview;
    private TextView txt_title;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private String url;

    public AlertWebViewDialog(Activity activity, Handler mHandler, String url) {
        this.activity = activity;
        this.mHandler = mHandler;
        this.url = url;
        WindowManager windowManager = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertWebViewDialog builder() {
        // 获取Dialog布局
        @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(
                R.layout.view_alertdialog_webview, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = view.findViewById(R.id.lLayout_bg);
        ll_webview = view.findViewById(R.id.ll_webview);
        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        btn_neg = view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        ll_line_1 = view.findViewById(R.id.ll_line_1);
        ll_line_2 = view.findViewById(R.id.ll_line_2);

        // 定义Dialog布局和参数
        dialog = new Dialog(activity, R.style.AlertDialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

        WebView mWeb = new WebView(activity);

        mWeb.loadUrl(url);
        WebSettings webSettings = mWeb.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDefaultFontSize(13);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);

        mWeb.addJavascriptInterface(new DecoObject(activity, mHandler), "decoObject");

        ll_webview.addView(mWeb);
        return this;
    }

    class DecoObject {
        private Activity mActivity;
        private Handler oHandler;

        public DecoObject(Activity activity, Handler mhandler) {
            this.mActivity = activity;
            this.oHandler = mhandler;
        }

        @JavascriptInterface
        public void showUserAgreement(final int action) {
            HandlerTip.getInstance().postDelayed(1000, new HandlerTip.HandlerCallback() {
                @Override
                public void postDelayed() {
                    oHandler.sendEmptyMessage(action);
                }
            });
        }
    }

    public AlertWebViewDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertWebViewDialog setTitleSize(float size) {
        txt_title.setTextSize(size);
        return this;
    }


    public AlertWebViewDialog setTitleColor(int color) {
        txt_title.setTextColor(ContextCompat.getColor(activity, color));
        return this;
    }

    public AlertWebViewDialog setTitleBold() {
        TextPaint tp = txt_title.getPaint();
        tp.setFakeBoldText(true);
        return this;
    }

    public AlertWebViewDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertWebViewDialog setPositiveButton(String text, final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertWebViewDialog setNegativeButton(String text,
                                                final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alert_dialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        if (!activity.isFinishing()) {
            setLayout();
            dialog.show();
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public AlertWebViewDialog setOnDismissListener(final DialogInterface.OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
        return this;
    }

    public void dismiss() {
        ll_webview.setVisibility(View.INVISIBLE);
        txt_title.setVisibility(View.INVISIBLE);
        btn_pos.setVisibility(View.INVISIBLE);
        btn_neg.setVisibility(View.INVISIBLE);
        ll_line_1.setVisibility(View.INVISIBLE);
        ll_line_2.setVisibility(View.INVISIBLE);
        dialog.dismiss();
    }

}