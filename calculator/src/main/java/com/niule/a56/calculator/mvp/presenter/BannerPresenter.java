package com.niule.a56.calculator.mvp.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.niule.a56.calculator.base.BasePresenter;
import com.niule.a56.calculator.http.XApi;
import com.hokaslibs.mvp.bean.Banner;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.mvp.contract.BannerContract;
import com.niule.a56.calculator.mvp.model.BannerModel;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.RequestBody;

import java.util.List;
@SuppressWarnings("Duplicates")
public class BannerPresenter extends BasePresenter<BannerContract.Model, BannerContract.View> {

    public BannerPresenter(Context context, BannerContract.View view) {
        super(new BannerModel(), view, context);
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }


    /**
     * 获取banner  广告  2 活  3 承接
     */
    public void getBanners(int type) {
        Banner bean = new Banner();
        bean.setBannerType(type);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(bean));
        mModel.getBanners(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<List<Banner>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<Banner>>>getScheduler())
                .compose(BasePresenter.<BaseObject<List<Banner>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<List<Banner>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<List<Banner>> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null && object.getData().size() > 0) {
                                mRootView.onBannerList(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });
    }
}