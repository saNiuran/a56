package com.niule.a56.calculator.jpush;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.flyco.animation.Attention.Swing;
import com.flyco.animation.ZoomExit.ZoomOutExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.mvp.ui.activity.*;
import com.niule.a56.calculator.utils.dialog.Hint2Dialog;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.bean.NotifyExtras;
import com.niule.a56.calculator.mvp.ui.activity.*;

/**
 * @Author Rich on 2020-12-20 16:34
 * @Projcet frog
 */
@SuppressWarnings("Duplicates")
public class NotifyDialog extends BaseDialog<NotifyDialog> {

    private static final String TAG = "NotifyDialog";
    TextView tvTitle;
    TextView tvContent;
    TextView tvBtn;
    TextView tvClose;

    private static NotifyDialog dialog = null;

    public NotifyDialog(Context context){
        super(context);
    }

    public static NotifyDialog getInstance(Context context) {
        if (dialog == null) {
            synchronized (Hint2Dialog.class) {
                if (dialog == null) {
                    dialog = new NotifyDialog(context);
                }
            }
        }
        return dialog;
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
                NotifyDialog.this.dismiss();
            }
        });
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

    private void toTestDismiss() {
        tvBtn.setText("我知道了");
        tvBtn.setVisibility(View.VISIBLE);
        tvClose.setVisibility(View.GONE);
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void gotoDetailsWork(String string, final NotifyExtras notifyExtras) {

        tvBtn.setText(notifyExtras.getNotificationTitle());
        tvContent.setText(notifyExtras.getContent());

        tvBtn.setText(string);
        tvBtn.setVisibility(View.VISIBLE);
        HokasUtils.logcat("orderNo==" + notifyExtras.getRelationId());
        if (!HokasUtils.isNoEmpty(notifyExtras.getRelationId())) {
            toTestDismiss();
        } else {
            tvBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent;
////                    if (notifyExtras.getRelationId().substring(0, 2).equals("00"))
////                        intent = new Intent(mContext, DetailsHuoActivity.class);
////                    else
////                        intent = new Intent(mContext, DetailsCjActivity.class);
//                    intent.putExtra("index", -1);
//                    intent.putExtra("no", notifyExtras.getRelationId());
//                    mContext.startActivity(intent);
//                    dismiss();
                }
            });
        }
    }

    public void gotoGeneralInfo(NotifyExtras notifyExtras) {
        Log.d(TAG,"gotoGeneralInfo=="+notifyExtras.toString());
        tvBtn.setText(notifyExtras.getNotificationTitle());
        tvContent.setText(notifyExtras.getContent());
        toTestDismiss();
    }

//    public void gotoSubscription(String string, final NotifyExtras notifyExtras) {
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, SubscriptionManagerActivity.class);
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }


//    public void gotoTopStickPay(String string, final NotifyExtras notifyExtras) {
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(mContext, TopPayActivity.class);
//                intent.putExtra("bean", notifyExtras.getRelationId());
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }
//
//
//    public void gotoMyHuo(String string, final NotifyExtras notifyExtras){
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, DetailsMyPostHuoActivity.class);
//                intent.putExtra("index", Integer.parseInt(notifyExtras.getRelationId()));
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }
//
//    public void gotoMyHuoList(String string, final NotifyExtras notifyExtras){
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, MyPostHuoActivity.class);
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }
//
//    public void gotoMyCjList(String string, final NotifyExtras notifyExtras){
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, MyPostCjActivity.class);
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }
//
//    public void gotoMyCouponList(String string, final NotifyExtras notifyExtras){
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, CardCouponActivity.class);
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }
//
//    public void gotoTransactionDetails(String string, final NotifyExtras notifyExtras){
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, TransactionDetailsActivity.class);
//                intent.putExtra("index", Integer.parseInt(notifyExtras.getRelationId()));
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }
//
//    public void gotoMallTransactionDetails(String string, final NotifyExtras notifyExtras){
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, DetailsMyMallTransactionActivity.class);
//                intent.putExtra("bean", Long.parseLong(notifyExtras.getRelationId()));
//                intent.putExtra("type", Integer.parseInt(notifyExtras.getUserType()));
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }
//
//    public void gotoMallCommodityDetails(String string, final NotifyExtras notifyExtras){
//        tvTitle.setText(notifyExtras.getNotificationTitle());
//        tvContent.setText(notifyExtras.getContent());
//
//        tvBtn.setText(string);
//        tvBtn.setVisibility(View.VISIBLE);
//        tvBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, DetailsMyCommodityActivity.class);
//                intent.putExtra("bean", Long.parseLong(notifyExtras.getRelationId()));
//                mContext.startActivity(intent);
//                dismiss();
//            }
//        });
//    }


}
