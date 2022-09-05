package com.niule.a56.calculator.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.niule.a56.calculator.base.BasePresenter;
import com.niule.a56.calculator.http.XApi;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.mvp.model.VersionModel;
import com.niule.a56.calculator.mvp.contract.VersionContract;

import com.niule.a56.calculator.utils.update.manager.AppVersion;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.RequestBody;

public class VersionPresenter extends BasePresenter<VersionContract.Model, VersionContract.View> {

    public VersionPresenter(Context context, VersionContract.View view) {
        super(new VersionModel(), view, context);
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }

    /**
     * 版本更新
     */
    public void getLastVersion() {
        AppVersion appVersion = new AppVersion();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(appVersion));
        mModel.getLastApkVersion(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<AppVersion>>getApiTransformer())
                .compose(XApi.<BaseObject<AppVersion>>getScheduler())
                .compose(BasePresenter.<BaseObject<AppVersion>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<AppVersion>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<AppVersion> baseObject) {
                        if (0 == baseObject.getRetCode()) {
                            mRootView.onVersion(baseObject.getData());
                        } else {
                            if (baseObject.getMessage() != null && !baseObject.getMessage().trim().isEmpty()) {
                                mRootView.showMessage(baseObject.getMessage());
                                Log.d(mContext.getClass().toString(), baseObject.getMessage().trim());
                            }
                        }
                    }
                });
    }
}