//package com.niule.a56.calculator.jpush;
//
//import android.app.NotificationManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Bundle;
//
//import com.google.gson.Gson;
//import com.hokaslibs.utils.Constant;
//import com.hokaslibs.utils.HokasUtils;
//import com.niule.a56.calculator.utils.PreferencesUtil;
//import com.hokaslibs.utils.UserManager;
//import com.niule.a56.calculator.MainActivity;
//import com.niule.a56.calculator.R;
//import com.niule.a56.calculator.base.BaseApplication;
//import com.niule.a56.calculator.bean.NotifyExtras;
//import com.niule.a56.calculator.huanxin.DemoHelper;
//import com.niule.a56.calculator.utils.dialog.BirthdayBlessingDialog;
//import com.niule.a56.calculator.utils.dialog.Hint2Dialog;
//import com.niule.a56.calculator.utils.dialog.RedDialog;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import cn.jpush.android.api.JPushInterface;
//
//import static android.content.Context.NOTIFICATION_SERVICE;
//import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
//
///**
// * 自定义接收器
// * <p>
// * 如果不定义这个 Receiver，则：
// * 1) 默认用户会打开主界面
// * 2) 接收不到自定义消息
// */
//@SuppressWarnings("Duplicates")
//public class MyReceiver extends BroadcastReceiver {
//
//    private NotificationManager nm;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        if (null == nm) {
//            nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        }
//        Bundle bundle = intent.getExtras();
//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            HokasUtils.logcat("JPush用户注册成功");
//
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            HokasUtils.logcat("接收到推送下来的自定义消息");
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            HokasUtils.logcat("接收到推送下来的通知");
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            HokasUtils.logcat("用户点击打开了通知");
////            openNotification(context, bundle);
//            receivingNotification(context, bundle);
//        } else {
//            HokasUtils.logcat("Unhandled intent - " + intent.getAction());
//        }
//    }
//
//    private void receivingNotification(Context context, Bundle bundle) {
//
//        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//        HokasUtils.logcat(" title : " + title);
//        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//        HokasUtils.logcat("message : " + message);
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        HokasUtils.logcat("extras : " + extras);
//
//        NotifyExtras notifyExtras = new NotifyExtras();
//        if (HokasUtils.isNoEmpty(extras)) {
//            notifyExtras = new Gson().fromJson(extras, NotifyExtras.class);
//        }
//
//        if (!UserManager.getInstance().getLoginStatus())
//            return;
//
//        if ("".equals(notifyExtras.getNotifyCode())) {
//            PreferencesUtil.setDataString(Constant.普通通知内容, notifyExtras.getContent());
//            PreferencesUtil.setDataString(Constant.普通通知标题, title);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.普通通知, true);
////            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.普通通知));
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.普通通知, false);
//                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setTitle(title);
//                dialog.setContent(notifyExtras.getContent());
//                dialog.toTestDismiss();
//            }
//        }
//
//
//        if ("201".equals(notifyExtras.getNotifyCode())) {   //订阅
//            playSound(context);
//            PreferencesUtil.setDataString(Constant.SUBSCRIBE_ID, notifyExtras.getRelationId());
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.SUBSCRIBE, true);
//                PreferencesUtil.setDataString(Constant.SUBSCRIBE_TITLE, title);
//                PreferencesUtil.setDataString(Constant.SUBSCRIBE_CONTENT, notifyExtras.getContent());
////                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.SUBSCRIBE));
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.SUBSCRIBE, false);
//                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setTitle(title);
//                dialog.setContent(notifyExtras.getContent());
//                dialog.toDetailsHuoActivity("去查看");
//            }
//        }
//        if ("202".equals(notifyExtras.getNotifyCode())) {   //进入订阅界面
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.TO_SUBSCRIBE, true);
//                PreferencesUtil.setDataString(Constant.TO_SUBSCRIBE_TITLE, title);
//                PreferencesUtil.setDataString(Constant.TO_SUBSCRIBE_CONTENT, notifyExtras.getContent());
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.TO_SUBSCRIBE, false);
//                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setTitle(title);
//                dialog.setContent(notifyExtras.getContent());
//                dialog.toSubscriptionManagerActivity("去订阅");
//            }
//        }
//        if ("501".equals(notifyExtras.getNotifyCode())) {   //红包
//            PreferencesUtil.setDataString(Constant.RED_PACK_IMG, notifyExtras.getImgUrl());
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.RED_PACK, true);
////                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.RED_PACK));
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.RED_PACK, false);
//                RedDialog dialog = new RedDialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setImgPath(notifyExtras.getImgUrl());
//            }
////            Intent intent = new Intent(context, MainActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            intent.putExtra("notifyCode", notifyCode);
////            intent.putExtra("imgUrl", imgUrl);
////            context.startActivity(intent);
//        }
//        if ("601".equals(notifyExtras.getNotifyCode())) {   //生日祝福
//            PreferencesUtil.setDataString(Constant.BIRTHDAY_BLESSING_IMG, notifyExtras.getImg());
//            PreferencesUtil.setDataString(Constant.BIRTHDAY_BLESSING_NAME, notifyExtras.getName());
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.BIRTHDAY_BLESSING, true);
////                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.BIRTHDAY_BLESSING));
//            } else {
//                PreferencesUtil.setDataBoolean(Constant.BIRTHDAY_BLESSING, false);
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                BirthdayBlessingDialog dialog = new BirthdayBlessingDialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setImgPath(notifyExtras.getImgUrl());
//                dialog.setName(notifyExtras.getName());
//            }
////            Intent intent = new Intent(context, MainActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            intent.putExtra("notifyCode", notifyCode);
////            intent.putExtra("imgUrl", imgUrl);
////            context.startActivity(intent);
//        }
//        if ("701".equals(notifyExtras.getNotifyCode())) {   //置顶审核成功通知 (跳转到置顶支付页面)
////            PreferencesUtil.setDataBoolean(Constant.TOP_AUDIT_SUCCESS, true);
//            PreferencesUtil.setDataString(Constant.TOP_ID, notifyExtras.getRelationId());
////            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.TOP_AUDIT_SUCCESS));
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.TOP_AUDIT_SUCCESS, true);
//                PreferencesUtil.setDataString(Constant.TOP_AUDIT_SUCCESS_CONTENT, notifyExtras.getContent());
//                PreferencesUtil.setDataString(Constant.TOP_AUDIT_SUCCESS_TITLE, title);
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.TOP_AUDIT_SUCCESS, false);
//                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setTitle(title);
//                dialog.setContent(notifyExtras.getContent());
//                dialog.toTopPayActivity("去查看");
//            }
//        }
//        if ("702".equals(notifyExtras.getNotifyCode())) {   //置顶审核失败通知 (跳转到这条进行中信息列表)
////            PreferencesUtil.setDataBoolean(Constant.TOP_AUDIT_FAILURE, true);
////            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.TOP_AUDIT_FAILURE));
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.TOP_AUDIT_FAILURE, true);
//                PreferencesUtil.setDataString(Constant.TOP_AUDIT_FAILURE_CONTENT, notifyExtras.getContent());
//                PreferencesUtil.setDataString(Constant.TOP_AUDIT_FAILURE_TITLE, title);
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.TOP_AUDIT_FAILURE, false);
//                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setTitle("提示");
//                dialog.setContent(notifyExtras.getContent());
//                dialog.toTestDismiss();
//            }
//        }
//        if ("800".equals(notifyExtras.getNotifyCode())) {   //报价详情
//            PreferencesUtil.setDataString(Constant.QUOTE_DETAILS_ID, notifyExtras.getRelationId());
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.QUOTE_DETAILS, true);
//                PreferencesUtil.setDataString(Constant.QUOTE_DETAILS_CONTENT, notifyExtras.getContent());
//                PreferencesUtil.setDataString(Constant.QUOTE_DETAILS_TITLE, title);
//            } else {
//                PreferencesUtil.setDataBoolean(Constant.QUOTE_DETAILS, false);
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setTitle(title);
//                dialog.setContent(notifyExtras.getContent());
//                dialog.toDetailsHuoPostActivity("去查看");
////                dialog.toTestDismiss();
//            }
////            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.QUOTE_DETAILS));
//        }
//        if ("900".equals(notifyExtras.getNotifyCode())) {   //交易通知
//            PreferencesUtil.setDataString(Constant.TRANSACTION_ID, notifyExtras.getRelationId());
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.TRANSACTION_INFO, true);
//                PreferencesUtil.setDataString(Constant.TRANSACTION_CONTENT, notifyExtras.getContent());
//                PreferencesUtil.setDataString(Constant.TRANSACTION_TITLE, title);
////                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.TRANSACTION_CONTENT));
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.TRANSACTION_INFO, false);
//                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                dialog.show();
//                dialog.setTitle(title);
//                dialog.setContent(notifyExtras.getContent());
//                dialog.toTransactionDetailsActivity("去查看");
////                new HandlerTip().postDelayed(5000, new HandlerTip.HandlerCallback() {
////                    @Override
////                    public void postDelayed() {
////                        dialog.dismiss();
////                    }
////                });
//            }
//        }
//
//        if (notifyExtras.getNotifyCode().length() == 4 && "19".equals(notifyExtras.getNotifyCode().substring(0, 2))) {   //交易类通知
//            PreferencesUtil.setDataString(Constant.MALL_TRANSACTION_ID, notifyExtras.getRelationId());
//            PreferencesUtil.setDataString(Constant.USER_TYPE, notifyExtras.getUserType());
//            PreferencesUtil.setDataString(Constant.MALL_TRANSACTION_CONTENT, notifyExtras.getContent());
//            PreferencesUtil.setDataString(Constant.MALL_TRANSACTION_TITLE, title);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.MALL_TRANSACTION_INFO, true); //置标志
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
////                Hint2Dialog dialog = new Hint2Dialog(BaseApplication.currentActivity());
//                if ((BaseApplication.activityList.size() > 0) && (null != BaseApplication.activityList.get(BaseApplication.activityList.size() - 1))) {
//                    Hint2Dialog dialog = new Hint2Dialog(BaseApplication.activityList.get(BaseApplication.activityList.size() - 1));
//                    dialog.show();
//                    dialog.setTitle(title);
//                    dialog.setContent(notifyExtras.getContent());
//                    dialog.toMallTransactionDetailsActivity("去查看");
//                } else {
//                    HokasUtils.logcat("交易类通知出错！");
//                }
//            }
//        }
//
//        //商品类通知
//        if (notifyExtras.getNotifyCode().length() == 4 && "16".equals(notifyExtras.getNotifyCode().substring(0, 2))) {
//            PreferencesUtil.setDataString(Constant.COMMODITY_ID, notifyExtras.getRelationId());
//            PreferencesUtil.setDataString(Constant.COMMODITY_CONTENT, notifyExtras.getContent());
//            PreferencesUtil.setDataString(Constant.COMMODITY_TITLE, title);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                PreferencesUtil.setDataBoolean(Constant.COMMODITY_INFO, true);
////            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent().setAction(Constant.普通通知));
//            } else {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                PreferencesUtil.setDataBoolean(Constant.COMMODITY_INFO, false);
//                if ((BaseApplication.activityList.size() > 0) && (null != BaseApplication.activityList.get(BaseApplication.activityList.size() - 1))) {
//                    Hint2Dialog dialog = new Hint2Dialog(BaseApplication.activityList.get(BaseApplication.activityList.size() - 1));
//                    dialog.show();
//                    dialog.setTitle(title);
//                    dialog.setContent(notifyExtras.getContent());
//                    dialog.toCommodityDetailsActivity("去查看");
////                    dialog.toTestDismiss();
//                } else {
//                    HokasUtils.logcat("商品类通知出错！");
//                }
//            }
//        }
//    }
//
//    private void openNotification(Context context, Bundle bundle) {
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        HokasUtils.logcat(extras);
//        String relationId = "";
//        String notifyCode = "";
//        String releaseType = "";
//        try {
//            JSONObject extrasJson = new JSONObject(extras);
//            relationId = extrasJson.optString("relationId");
//            notifyCode = extrasJson.optString("notifyCode");
//            releaseType = extrasJson.optString("releaseType");
//        } catch (Exception e) {
//            HokasUtils.logcat("Unexpected: extras is not a valid json");
//            return;
//        }
//        HokasUtils.logcat(relationId);
//        HokasUtils.logcat(notifyCode);
//        if (!UserManager.getInstance().getLoginStatus())
//            return;
//        if ("401".equals(notifyCode) || "501".equals(notifyCode) || "601".equals(notifyCode)) {   //异地登录
//            Intent intent = new Intent(context, MainActivity.class);
//            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
//        if ("201".equals(notifyCode)) {  //关键词订阅
////            Intent intent= new Intent();
////            if(HokasUtils.isNoEmpty(relationId)) {
////                if (relationId.substring(0, 2).equals("00"))
////                    intent.setClass(context, DetailsHuoActivity.class);
////                else
////                    intent.setClass(context, DetailsCjActivity.class);
////            }
////            intent.putExtra("index", -1);
////            intent.putExtra("no", relationId);
////            Intent intent = new Intent(context, DetailsHuoActivity.class);
////            intent.putExtra("index", Integer.parseInt(relationId));
////            intent.putExtra("type", 10);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            context.startActivity(intent);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Intent intent = new Intent(BaseApplication.currentActivity(), BaseApplication.currentActivity().getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                BaseApplication.currentActivity().startActivity(intent);
//            }
//
//        }
//        if ("701".equals(notifyCode)) {   //置顶审核成功通知 (跳转到置顶支付页面)
////            Intent intent = new Intent(context, MainActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            intent.putExtra("index", Integer.parseInt(relationId));
////            context.startActivity(intent);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Intent intent = new Intent(BaseApplication.currentActivity(), BaseApplication.currentActivity().getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                BaseApplication.currentActivity().startActivity(intent);
//            }
//        }
//        if ("702".equals(notifyCode)) {   //置顶审核失败通知 (跳转到这条进行中信息列表)
////            Intent intent = new Intent(context, MainActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            intent.putExtra("index", Integer.parseInt(relationId));
////            context.startActivity(intent);
////            Intent intent = new Intent(context, MyActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            if ("1".equals(releaseType)) //承接
////                intent.putExtra("index", 16);
////            else                        //活信息
////                intent.putExtra("index", 17);
////            context.startActivity(intent);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Intent intent = new Intent(BaseApplication.currentActivity(), BaseApplication.currentActivity().getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                BaseApplication.currentActivity().startActivity(intent);
//            }
//        }
//        if ("703".equals(notifyCode)) {   //置顶券推送， 获取到新的置顶券
////            Intent intent = new Intent(context, CardCouponActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            context.startActivity(intent);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Intent intent = new Intent(BaseApplication.currentActivity(), BaseApplication.currentActivity().getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                BaseApplication.currentActivity().startActivity(intent);
//            }
//        }
//        if ("704".equals(notifyCode)) {   //置顶即将结束通知  (跳转到这条进行中信息列表)
////            Intent intent = new Intent(context, MyPostHuoActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            intent.putExtra("index", Integer.parseInt(relationId));
////            context.startActivity(intent);
////            Intent intent = new Intent(context, MyActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            if ("1".equals(releaseType)) //承接
////                intent.putExtra("index", 16);
////            else                        //活信息
////                intent.putExtra("index", 17);
////            context.startActivity(intent);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Intent intent = new Intent(BaseApplication.currentActivity(), BaseApplication.currentActivity().getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                BaseApplication.currentActivity().startActivity(intent);
//            }
//        }
//        if ("800".equals(notifyCode)) {   //报价详情
////            Intent intent = new Intent(context, DetailsMyPostHuoActivity.class);
////            intent.putExtra("index", Integer.parseInt(relationId));
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            context.startActivity(intent);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Intent intent = new Intent(BaseApplication.currentActivity(), BaseApplication.currentActivity().getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                BaseApplication.currentActivity().startActivity(intent);
//            }
//        }
//        if ("900".equals(notifyCode)) {   //交易通知
////            Intent intent = new Intent(context, MainActivity.class);
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            context.startActivity(intent);
////            Intent intent = new Intent(context, TransactionDetailsActivity.class);
////            intent.putExtra("index", Integer.parseInt(relationId));
////            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
////            context.startActivity(intent);
//            if (PreferencesUtil.getDataBoolean(Constant.IS_HOME)) {
//                if (BaseApplication.currentActivity() == null)
//                    return;
//                Intent intent = new Intent(BaseApplication.currentActivity(), BaseApplication.currentActivity().getClass());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                BaseApplication.currentActivity().startActivity(intent);
//            }
//        }
//    }
//
//
//    private static Ringtone mRingtone;
//
//    //播放自定义的声音
//    public synchronized void playSound(Context context) {
//        if (mRingtone == null) {
//            HokasUtils.logcat("----------初始化铃声----------");
//            String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.subscription;
//            Uri no = Uri.parse(uri);
//            mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), no);
//        }
//        if (!mRingtone.isPlaying()) {
//            HokasUtils.logcat("--------------播放铃声---------------" + mRingtone.isPlaying());
//            mRingtone.play();
//        }
//    }
//
//
//}
