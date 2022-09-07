package com.niule.a56.calculator.mvp.presenter;

import android.content.Context;
import com.google.gson.Gson;
import com.hokaslibs.utils.BitmapUtils;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.base.BasePresenter;
import com.niule.a56.calculator.bean.ContinentPack;
import com.niule.a56.calculator.bean.Enquiry;
import com.niule.a56.calculator.bean.EnquiryRequest;
import com.niule.a56.calculator.bean.ImagePath;
import com.niule.a56.calculator.http.XApi;
import com.niule.a56.calculator.mvp.contract.EnquiryContract;
import com.niule.a56.calculator.mvp.model.EnquiryModel;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;

public class EnquiryPresenter extends BasePresenter<EnquiryContract.Model, EnquiryContract.View> {

    public EnquiryPresenter(Context context, EnquiryContract.View view) {
        super(new EnquiryModel(), view, context);
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }

    /**
     * 获取 国家选择器 数据
     */
    public void getContinentPackList() {
        mModel.getContinentPackList()
                .retryWhen(new RetryWithDelay(2, 10))
                .compose(XApi.<BaseObject<List<ContinentPack>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<ContinentPack>>>getScheduler())
                .compose(BasePresenter.<BaseObject<List<ContinentPack>>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseObject<List<ContinentPack>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<List<ContinentPack>> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null && object.getData().size() > 0) {
                                mRootView.onContinentPackData(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });
    }

    //上传图片
    public void uploadImg(final String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
        HokasUtils.logcat("fileName ="+fileName);
        String path = BitmapUtils.bitmapToString(filePath, mContext);
        File file;
        if (path != null) {
            file = new File(path);
        } else
            file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "android_" + fileName, requestFile);
        mModel.upLoadImage(part)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<List<ImagePath>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<ImagePath>>>getScheduler())
                .doOnError(throwable -> mRootView.showMessage(throwable.getMessage()))
                .compose(BasePresenter.<BaseObject<List<ImagePath>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<List<ImagePath>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<List<ImagePath>> object) {
                        if (0 == object.getRetCode() && object.getData().size()>0) {
                            mRootView.onUpImgDone(object.getData());
                        } else {
                            mRootView.hideLoading();
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });

    }

    /**
     * 创建询盘
     */
    public void createEnquiry(EnquiryRequest request) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(request));
        mModel.createEnquiry(body)
                .retryWhen(new RetryWithDelay(2, 10))
                .compose(XApi.<BaseObject<Enquiry>>getApiTransformer())
                .compose(XApi.<BaseObject<Enquiry>>getScheduler())
                .compose(BasePresenter.<BaseObject<Enquiry>>bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseObject<Enquiry>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<Enquiry> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null) {
                                mRootView.onCreateEnquirySuccess(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });
    }
}