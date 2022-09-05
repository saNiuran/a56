//package com.niule.a56.calculator.jpush;
//
//import android.content.Context;
//import cn.jpush.android.api.JPushInterface;
//import com.blankj.utilcode.util.DeviceUtils;
//import com.niule.a56.calculator.base.BaseApplication;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static com.niule.a56.calculator.jpush.TagAliasOperatorHelper.*;
//
///**
// * Created by administor on 2017/10/25.
// */
//
//public class JPushUtils {
//    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
//    public static void setAlias() {
//        // 调用 Handler 来异步设置别名
//        sequence++;
//        TagAliasOperatorHelper.TagAliasBean bean = new TagAliasOperatorHelper.TagAliasBean();
//        bean.action = 2;
//        bean.isAliasAction = true;
//        bean.alias = DeviceUtils.getAndroidID();
//        TagAliasOperatorHelper.getInstance().handleAction(BaseApplication.getInstance(), sequence, bean);
//    }
//
//    public static void deleteAlias() {
//        // 调用 Handler 来异步删除别名
//        sequence++;
//        TagAliasOperatorHelper.TagAliasBean bean = new TagAliasOperatorHelper.TagAliasBean();
//        bean.action = 3;
//        bean.isAliasAction = true;
//        bean.alias = DeviceUtils.getAndroidID();
//        TagAliasOperatorHelper.getInstance().handleAction(BaseApplication.getInstance(), sequence, bean);
//    }
//
//    public static void addTag(String phone) {
//        // 调用 Handler 来异步删除别名
//        sequence++;
//        TagAliasOperatorHelper.TagAliasBean bean = new TagAliasOperatorHelper.TagAliasBean();
//        bean.action = 1;
//        bean.isAliasAction = false;
//        Set<String> strings = new HashSet<>();
//        strings.add(phone);
//        bean.tags = strings;
//        TagAliasOperatorHelper.getInstance().handleAction(BaseApplication.getInstance(), sequence, bean);
//    }
//
//
//    public static void checkTag(String phone) {
//        // 调用 Handler 获取所有Tag
//        sequence++;
//        TagAliasOperatorHelper.TagAliasBean bean = new TagAliasOperatorHelper.TagAliasBean();
//        bean.action = ACTION_CHECK;
//        bean.isAliasAction = false;
//        Set<String> strings = new HashSet<>();
//        strings.add(phone);
//        bean.tags = strings;
//        TagAliasOperatorHelper.getInstance().handleAction(BaseApplication.getInstance(), sequence, bean);
//    }
//
//    public static void deleteTag() {
//        // 调用 Handler 来异步删除别名
//        sequence++;
//        TagAliasOperatorHelper.TagAliasBean bean = new TagAliasOperatorHelper.TagAliasBean();
//        bean.action = 4;
//        bean.isAliasAction = false;
//        TagAliasOperatorHelper.getInstance().handleAction(BaseApplication.getInstance(), sequence, bean);
//    }
////    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
////    public static void setAlias() {
////        // 调用 Handler 来异步设置别名
////        mHandler.sendEmptyMessage(MSG_SET_ALIAS);
////    }
////
////    public static void deleteAlias() {
////        // 调用 Handler 来异步删除别名
////        mHandler.sendEmptyMessage(MSG_UN_ALIAS);
////    }
////
////    private static final int MSG_SET_ALIAS = 1001;
////    private static final int MSG_UN_ALIAS = 1002;
////    private static final Handler mHandler = new Handler() {
////        @Override
////        public void handleMessage(android.os.Message msg) {
////            super.handleMessage(msg);
////            switch (msg.what) {
////                case MSG_SET_ALIAS:
////                    Log.d("JPushUtils", "Set alias in handler.");
////                    // 调用 JPush 接口来设置别名。
////                    break;
////                case MSG_UN_ALIAS:
////                    Log.d("JPushUtils", "delete alias in handler.");
////                    // 调用 JPush 接口来设置别名。
////                    JPushInterface.deleteAlias(BaseApplication.getInstance(), 1003);
////                    break;
////                default:
////                    Log.i("JPushUtils", "Unhandled msg - " + msg.what);
////            }
////        }
////    };
//}
