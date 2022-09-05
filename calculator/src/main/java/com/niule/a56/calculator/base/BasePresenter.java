package com.niule.a56.calculator.base;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.niule.a56.calculator.utils.PermissionUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<M, V extends BaseView> implements presenter, ResponseErroListener {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeSubscription mCompositeSubscription;
    protected Gson gson;
    protected M mModel;
    protected V mRootView;
    protected RxErrorHandler mErrorHandler;
    protected RxPermissions mRxPermissions;
    protected Context mContext;

    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter(M model, V rootView, Context context) {
        this.mContext = context;
        this.mModel = model;
        this.mRootView = rootView;
        this.mErrorHandler = RxErrorHandler.builder().with(context).responseErroListener(this).build();
        this.mRxPermissions = new RxPermissions((Activity) context);
        onStart();
    }

    public void permission() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }
        }, mRxPermissions, mRootView, mErrorHandler);
    }

    public BasePresenter() {
        onStart();
    }


    @Override
    public void onStart() {
        gson = new Gson();
//        if (useEventBus())//如果要使用eventbus请将此方法返回true
//            EventBus.getDefault().register(this);//注册eventbus
    }

    @Override
    public void onDestroy() {
//        if (useEventBus())//如果要使用eventbus请将此方法返回true
//            EventBus.getDefault().unregister(this);//解除注册eventbus
        unSubscribe();//解除订阅
        this.mModel = null;
        this.mRootView = null;
    }

    protected void handleError(Throwable throwable) {

    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     */
    protected boolean useEventBus() {
        return false;
    }


    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);//将所有subscription放入,集中处理
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
        }
    }

    @Override
    public void unSubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
        }
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseView view) {
        if (view instanceof XActivity) {
            return ((XActivity) view).bindToLifecycle();
        } else if (view instanceof XFragment) {
            return ((XFragment) view).bindToLifecycle();
        } else if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindToLifecycle();
        }  else if (view instanceof RxFragment) {
            return ((RxFragment) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        Log.d("BasePresenter", "handleResponseError");
        mRootView.hideLoading();
    }
}
