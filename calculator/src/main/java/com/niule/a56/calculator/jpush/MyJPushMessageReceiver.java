package com.niule.a56.calculator.jpush;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.jpush.android.api.*;
import cn.jpush.android.service.JPushMessageReceiver;
import com.hokaslibs.utils.Constant;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.MainActivity;
import com.niule.a56.calculator.utils.AppBadgeUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 自定义JPush message 接收器,包括操作tag/alias的结果返回(仅仅包含tag/alias新接口部分)
 */
@SuppressWarnings("Duplicates")
public class MyJPushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "MyJPushMessageReceiver";

    //自定义消息
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context,customMessage);
        PreferencesUtil.setDataBoolean(Constant.RED_ITEM, true);  //显示小红点，在个人中心

        Log.e(TAG, "[onMessage] " + customMessage);

        boolean foreground = isForeground(context);
        if(foreground) {
            processCustomMessage(context, customMessage);
        }else{
            if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
                new AppBadgeUtil().setXiaoMiBadgeNum(context);
            }
            if (Build.MANUFACTURER.equalsIgnoreCase("huawei")) {
                new AppBadgeUtil().setHuaWeiBadgeNum(context);
            }
            if (Build.MANUFACTURER.equalsIgnoreCase("vivo")) {
                new AppBadgeUtil().setVivoBadgeNum(context);
            }
        }
    }

    //通知点击打开
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageOpened] " + message);
        if (!MainActivity.isForeground) {
            Log.e(TAG, "[onNotifyMessageOpened] 打开自定义的Activity, 拉起MainActivity");
            try {
                Intent i = new Intent(Intent.ACTION_MAIN);
                Bundle bundle = new Bundle();
                bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle);
                bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent);
                bundle.putString(MainActivity.KEY_EXTRAS, message.notificationExtras);
                i.putExtras(bundle);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setComponent(new ComponentName(context, "com.niule.a56.calculator.MainActivity"));
                context.startActivity(i);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            //MainActivity在前台运行的时候, 发送广播
            Log.e(TAG, "[onNotifyMessageOpened] 在运行的时候, 发送广播");
            Intent msgIntent = new Intent(MainActivity.MESSAGE_OPENED_ACTION);
            Bundle bundle = new Bundle();
            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle);
            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent);
            String extras = message.notificationExtras;
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        bundle.putString(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {
                    Log.e(TAG,"JSON解析错误！");
                }
            }
            msgIntent.putExtras(bundle);
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        switch (nActionExtra) {
            case "my_extra1":
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
                break;
            case "my_extra2":
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
                break;
            case "my_extra3":
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
                break;
            default:
                Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
                break;
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //send msg to MainActivity; 自定义消息处理
    private void processCustomMessage(Context context, CustomMessage customMessage) {
        String message = customMessage.message;
        String extras = customMessage.extra;
        Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
        if (!ExampleUtil.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (extraJson.length() > 0) {
                    msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                }
            } catch (JSONException e) {
                Log.e(TAG,"JSON解析错误！");
            }
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        Log.e(TAG, "[onNotificationSettingsCheck] isOn:" + isOn + ",source:" + source);
    }

    //当前应用是否处于前台
    public static boolean isForeground(Context context) {
        android.app.ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName());
    }
}
