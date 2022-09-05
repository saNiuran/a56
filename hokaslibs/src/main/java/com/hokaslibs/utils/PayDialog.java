package com.hokaslibs.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.hokaslibs.R;
import com.jungly.gridpasswordview.GridPasswordView;
import com.zhy.autolayout.AutoLinearLayout;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * 作者： Hokas
 * 时间： 2016/11/18
 * 类别：
 */

public class PayDialog extends Dialog {

    public PayDialog(Context context) {
        super(context);
    }

    public PayDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context context;
        private String title;
        private String money;
        private String moneyType;
        private GridPasswordView pasView;
        private View allMoney;
        private PayDialog dialog;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public PayDialog.Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public void setMoney(String money) {
            this.money = money;
        }

        public void setMoneyType(String moneyType) {
            this.moneyType = moneyType;
        }

        public GridPasswordView getPasView() {
            return pasView;
        }

        public void setVisibility(boolean flag) {
            if (flag)
                allMoney.setVisibility(View.VISIBLE);
            else
                allMoney.setVisibility(View.GONE);
        }

        public void dismiss() {
            dialog.dismiss();
        }


        public PayDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new PayDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_pay, null);
            dialog.addContentView(view, new AutoLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
            ((TextView) view.findViewById(R.id.tvTitle)).setText(title);
            ((TextView) view.findViewById(R.id.tvMoney)).setText(money);
            ((TextView) view.findViewById(R.id.tvMoneyType)).setText(moneyType);
            allMoney = view.findViewById(R.id.allMoney);
            view.findViewById(R.id.ivCloss).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            pasView = view.findViewById(R.id.pswView);
            HokasUtils.logcat("pasView.getChildCount()"+pasView.getChildCount());
            dialog.setOnShowListener(new OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    pasView.getChildAt(0).setFocusable(true);
                    pasView.getChildAt(0).setFocusableInTouchMode(true);
                    pasView.getChildAt(0).requestFocus();
                    pasView.getChildAt(0).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(pasView.getChildAt(0), InputMethodManager.SHOW_IMPLICIT);
                        }
                    }, 200);
                }

            });
            return dialog;
        }



    }
}
