package com.niule.a56.calculator.mvp.presenter;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.niule.a56.calculator.base.BaseApplication;
import com.niule.a56.calculator.base.BasePresenter;
import com.niule.a56.calculator.bean.ApkVersionRequest;
import com.niule.a56.calculator.bean.UserAn;
import com.niule.a56.calculator.http.XApi;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.mvp.model.VersionModel;
import com.niule.a56.calculator.mvp.contract.VersionContract;

import com.niule.a56.calculator.utils.update.manager.ApkVersion;
import com.niule.a56.calculator.utils.update.utils.DeviceUtils;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.RequestBody;

import java.util.Locale;

public class VersionPresenter extends BasePresenter<VersionContract.Model, VersionContract.View> {

    public VersionPresenter(Context context, VersionContract.View view) {
        super(new VersionModel(), view, context);
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }

    /**
     * 版本更新  digit '0=32位 1=64位'   archit '0=arm 1=intel 2=mips'
     */
    public void getLastVersion() {
        ApkVersionRequest request = getApkVersionRequest();

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(request));
        mModel.getLastApkVersion(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<ApkVersion>>getApiTransformer())
                .compose(XApi.<BaseObject<ApkVersion>>getScheduler())
                .compose(BasePresenter.<BaseObject<ApkVersion>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<ApkVersion>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<ApkVersion> baseObject) {
                        if (0 == baseObject.getRetCode()) {
                            mRootView.onVersion(baseObject.getData());
//                        } else {
//                            if (baseObject.getMessage() != null && !baseObject.getMessage().trim().isEmpty()) {
//                                mRootView.showMessage(baseObject.getMessage());
//                            }
                        }
                    }
                });
    }

    @NonNull
    private ApkVersionRequest getApkVersionRequest() {
        ApkVersionRequest request = new ApkVersionRequest();
        request.setVersionCode(String.valueOf(DeviceUtils.getVersionCode(BaseApplication.getInstance())));
        String cpuArch = Build.SUPPORTED_ABIS[0];
        int digit;
        int archit;
        if(cpuArch.toLowerCase(Locale.ROOT).contains("arm")){
            archit = 0;
        }else if(cpuArch.toLowerCase(Locale.ROOT).contains("x86")){
            archit = 1;
        }else{
            archit = 2;
        }
        if(cpuArch.toLowerCase(Locale.ROOT).contains("64")){
            digit = 1;
        }else{
            digit = 0;
        }
        request.setDigit(digit);
        request.setArchit(archit);

        UserAn userAn = new UserAn();
        userAn.setManufacturer(Build.MANUFACTURER);
        userAn.setBrand(Build.BRAND);
        userAn.setModel(Build.MODEL);

        request.setUserAn(userAn);
        return request;
    }

}