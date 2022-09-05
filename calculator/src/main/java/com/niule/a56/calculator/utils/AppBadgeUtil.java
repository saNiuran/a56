package com.niule.a56.calculator.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import com.niule.a56.calculator.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AppBadgeUtil {

    private static int num;

    public static void clearNum(){
        num = 0;
    }

    public void setHuaWeiBadgeNum(Context context) {
        try {
            Bundle bunlde = new Bundle();
            bunlde.putString("package", context.getPackageName());
            bunlde.putString("class", "com.niule.a56.calculator.SplashActivity");//此处为启动页路径
            bunlde.putInt("badgenumber", num++);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 小米手机创建通知信息并创建角标
     * @param context
     */
    public void setXiaoMiBadgeNum(Context context) {
        try{
            // num++ //这里不需要相加数量，小米自己会计算条数
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String title = "消息提示";
            String desc = "您有" + num + "条未读消息";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                String channelId = "default";
                String channelName = "默认通知";
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                channel.setShowBadge(true);
                notificationManager.createNotificationChannel(channel);
            }

            Notification notification = new NotificationCompat.Builder(context, "default")
                    .setContentTitle(title)
                    .setContentText(desc)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setChannelId("default")
//                    .setNumber(num)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .build();


            notificationManager.cancel(1);
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, num);
            notificationManager.notify(1, notification);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * vivo手机创建角标
     * 只有非vivo推送渠道并且8.0以上手机才可以使用下列方式
     * @param context
     */
    @SuppressLint("WrongConstant")
    public void setVivoBadgeNum(Context context) {
        num++;
        Intent intent = new Intent();
        intent.setAction("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.addFlags(0x01000000);
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", "com.niule.a56.calculator.SplashActivity");
        intent.putExtra("notificationNum", num);
        context.sendBroadcast(intent);
    }
}
