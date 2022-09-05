package com.niule.a56.calculator.mvp.presenter;

import android.content.Context;
import com.google.gson.Gson;
import com.niule.a56.calculator.base.BasePresenter;
import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.http.XApi;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.mvp.contract.FreightContract;
import com.niule.a56.calculator.mvp.model.FreightModel;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.RequestBody;

import java.util.List;

public class FreightPresenter extends BasePresenter<FreightContract.Model, FreightContract.View> {

    public FreightPresenter(Context context, FreightContract.View view) {
        super(new FreightModel(), view, context);
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
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<List<ContinentPack>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<ContinentPack>>>getScheduler())
                .compose(BasePresenter.<BaseObject<List<ContinentPack>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
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

    /**
     * 获取 航线选择器 数据
     */
    public void getFreightLineListOfCountry(long countryId) {
        Country country = new Country();
        country.setId(countryId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(country));
        mModel.getFreightPackList(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<List<FreightPack>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<FreightPack>>>getScheduler())
                .compose(BasePresenter.<BaseObject<List<FreightPack>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<List<FreightPack>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<List<FreightPack>> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null && object.getData().size() > 0) {
                                mRootView.onFreightPackData(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null) {
                                if (object.getMessage().contains("信息不存在")) {
                                    mRootView.onFreightPackError();
                                } else {
                                    mRootView.showMessage(object.getMessage());
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 获取 分区选择器 数据
     */
    public void getDivisionPackOfFreightLine(long freightLineId) {
        FreightLine freightLine = new FreightLine();
        freightLine.setId(freightLineId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(freightLine));
        mModel.getDivisionPack(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<DivisionPack>>getApiTransformer())
                .compose(XApi.<BaseObject<DivisionPack>>getScheduler())
                .compose(BasePresenter.<BaseObject<DivisionPack>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<DivisionPack>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<DivisionPack> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null) {
                                mRootView.onDivisionPackData(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });
    }

    public void getPriceRow(Options options) {
        PriceRow priceRow = new PriceRow();
        priceRow.setCargoId(1); //默认普货
        priceRow.setFreightLineId(options.getFreightLineId());
        priceRow.setDivisionId(options.getDivisionId());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(priceRow));

        mModel.validPriceRow(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<PriceRow>>getApiTransformer())
                .compose(XApi.<BaseObject<PriceRow>>getScheduler())
                .compose(BasePresenter.<BaseObject<PriceRow>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<PriceRow>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<PriceRow> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null) {
                                mRootView.onPriceRowData(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });
    }
}