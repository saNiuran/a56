package com.niule.a56.calculator.utils;

import android.Manifest;
import android.util.Log;

import com.niule.a56.calculator.base.BaseView;
import com.niule.a56.calculator.base.XActivity;
import com.niule.a56.calculator.base.XFragment;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.LifecycleTransformer;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * Created by jess on 17/10/2016 10:09
 * Contact with jess.yan.effort@gmail.com
 */

public class PermissionUtil {
    public static final String TAG = "Permission";


    public interface RequestPermission {
        void onRequestPermissionSuccess();
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseView view) {
        if (view instanceof XActivity) {
            return ((XActivity) view).<T>bindToLifecycle();
        } else if (view instanceof XFragment) {
            return ((XFragment) view).<T>bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }

    }


    /**
     * 请求摄像头权限
     */
    public static void launchCamera(final RequestPermission requestPermission, RxPermissions rxPermissions, final BaseView view, RxErrorHandler errorHandler) {
        //先确保是否已经申请过摄像头，和写入外部存储的权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                        rxPermissions
                                .isGranted(Manifest.permission.CAMERA);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.CAMERA)
                    .compose(PermissionUtil.<Boolean>bindToLifecycle(view))//使用RXlifecycle,使subscription和activity一起销毁
                    .subscribe(new ErrorHandleSubscriber<Boolean>(errorHandler) {
                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                Log.d(TAG,"request WRITE_EXTERNAL_STORAGE and CAMERA success");
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                                view.showMessage("request permissons failure");
                            }
                        }
                    });
        }
    }


    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(final RequestPermission requestPermission, RxPermissions rxPermissions, final BaseView view, RxErrorHandler errorHandler) {
        //先确保是否已经申请过摄像头，和写入外部存储的权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .compose(PermissionUtil.<Boolean>bindToLifecycle(view))//使用RXlifecycle,使subscription和activity一起销毁
                    .subscribe(new ErrorHandleSubscriber<Boolean>(errorHandler) {
                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                Log.d(TAG,"request WRITE_EXTERNAL_STORAGE and CAMERA success");
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                                view.showMessage("request permissons failure");
                            }
                        }
                    });
        }
    }


    /**
     * 请求发送短信权限
     */
    public static void sendSms(final RequestPermission requestPermission, RxPermissions rxPermissions, final BaseView view, RxErrorHandler errorHandler) {
//先确保是否已经申请过权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.SEND_SMS);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.SEND_SMS)
                    .compose(PermissionUtil.<Boolean>bindToLifecycle(view))//使用RXlifecycle,使subscription和activity一起销毁
                    .subscribe(new ErrorHandleSubscriber<Boolean>(errorHandler) {
                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                Log.d(TAG,"request SEND_SMS success");
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                                view.showMessage("request permissons failure");
                            }
                        }
                    });
        }
    }


    /**
     * 请求打电话权限
     */
    public static void callPhone(final RequestPermission requestPermission, RxPermissions rxPermissions, final BaseView view, RxErrorHandler errorHandler) {
//先确保是否已经申请过权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.CALL_PHONE);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.CALL_PHONE)
                    .compose(PermissionUtil.<Boolean>bindToLifecycle(view))//使用RXlifecycle,使subscription和activity一起销毁
                    .subscribe(new ErrorHandleSubscriber<Boolean>(errorHandler) {
                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                Log.d(TAG,"request SEND_SMS success");
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                                view.showMessage("request permissons failure");
                            }
                        }
                    });
        }
    }


    /**
     * 请求获取手机状态的权限
     */
    public static void readPhonestate(final RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
//先确保是否已经申请过权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.READ_PHONE_STATE);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new ErrorHandleSubscriber<Boolean>(errorHandler) {
                        @Override
                        public void onNext(Boolean granted) {
                            if (granted) {
                                Log.d(TAG,"request SEND_SMS success");
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                                Log.d(TAG,"request permissons failure");
                            }
                        }
                    });
        }
    }

}

