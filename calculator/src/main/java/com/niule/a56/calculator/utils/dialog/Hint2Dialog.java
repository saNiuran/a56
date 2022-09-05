package com.niule.a56.calculator.utils.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.flyco.animation.Attention.Swing;
import com.flyco.animation.ZoomExit.ZoomOutExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.niule.a56.calculator.R;

/**
 * 提示对话框
 */
@SuppressWarnings("Duplicates")
public class Hint2Dialog extends BaseDialog<Hint2Dialog> {

    TextView tvTitle;
    TextView tvContent;
    TextView tvBtn;
    TextView tvClose;

    private static Hint2Dialog alertDialog = null;

    public static Hint2Dialog getInstance(Context context) {
        if (alertDialog == null) {
            synchronized (Hint2Dialog.class) {
                if (alertDialog == null) {
                    alertDialog = new Hint2Dialog(context);
                }
            }
        }
        return alertDialog;
    }

    public Hint2Dialog(Context context) {
        super(context);
    }

    public Hint2Dialog setContent(String content) {
        SpannableStringBuilder span = new SpannableStringBuilder("缩进" + content);
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvContent.setText(span);
        return this;
    }

    public Hint2Dialog toTestDismiss() {
        tvBtn.setText("我知道了");
        setBtnVisibility(true);
        setCloseVisibility(false);
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return this;
    }

    public Hint2Dialog setBtnVisibility(boolean flag) {
        if (flag)
            tvBtn.setVisibility(View.VISIBLE);
        else
            tvBtn.setVisibility(View.GONE);
        return this;
    }

    public Hint2Dialog setCloseVisibility(boolean flag) {
        if (flag)
            tvClose.setVisibility(View.VISIBLE);
        else
            tvClose.setVisibility(View.GONE);
        return this;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new Swing());
        dismissAnim(new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_hint2, null);
        tvTitle = inflate.findViewById(R.id.tvTitle);
        tvContent = inflate.findViewById(R.id.tvContent);
        tvBtn = inflate.findViewById(R.id.tvBtn);
        tvClose = inflate.findViewById(R.id.tvClose);
        tvClose.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hint2Dialog.this.dismiss();
            }
        });
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

}
