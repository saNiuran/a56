package com.niule.a56.calculator.mvp.presenter;

import android.content.Context;
import com.google.gson.Gson;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.base.BasePresenter;
import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.http.XApi;
import com.niule.a56.calculator.mvp.contract.FreightContract;
import com.niule.a56.calculator.mvp.contract.PriceContract;
import com.niule.a56.calculator.mvp.model.FreightModel;
import com.niule.a56.calculator.mvp.model.PriceModel;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.RequestBody;

import java.util.List;

public class PricePresenter extends BasePresenter<PriceContract.Model, PriceContract.View> {

    public PricePresenter(Context context, PriceContract.View view) {
        super(new PriceModel(), view, context);
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }


    /**
     * 获取 计费单位列表
     */
    public void getChargeUnitList() {
        mModel.getChargeUnitList()
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<List<ChargeUnit>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<ChargeUnit>>>getScheduler())
                .compose(BasePresenter.<BaseObject<List<ChargeUnit>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<List<ChargeUnit>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<List<ChargeUnit>> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null && object.getData().size() > 0) {
                                mRootView.onChargeUnitListData(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });
    }

    public void getPriceItemPack(Options options){
        Options op = new Options();
        op.setFreightLineId(options.getFreightLineId());
        op.setDivisionId(options.getDivisionId());
        op.setCargoId(options.getCargoId());
        op.setTotalWeight(options.getTotalWeight()*100);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(op));

        mModel.getPriceItemPack(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<PriceItemPack>>getApiTransformer())
                .compose(XApi.<BaseObject<PriceItemPack>>getScheduler())
                .compose(BasePresenter.<BaseObject<PriceItemPack>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<PriceItemPack>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<PriceItemPack> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null) {
                                mRootView.onPriceItemPackData(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null){
                                if(object.getMessage().contains("最低起运")){
                                    mRootView.onLessThanMinChargeWeight(object.getMessage());
                                }else{
                                    mRootView.showMessage(object.getMessage());
                                }
                            }
                        }
                    }
                });
    }

    public void getGeneralMemoCombo(PriceRow priceRow){
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(priceRow));

        mModel.getGeneralMemoCombo(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<List<GeneralMemo>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<GeneralMemo>>>getScheduler())
                .compose(BasePresenter.<BaseObject<List<GeneralMemo>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<List<GeneralMemo>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<List<GeneralMemo>> object) {
                        mRootView.hideLoading();
                        if (0 == object.getRetCode()) {
                            if (object.getData() != null && object.getData().size()>0) {
                                mRootView.onGeneralMemoComboData(object.getData());
                            }
                        } else {
                            if (object.getMessage() != null)
                                mRootView.showMessage(object.getMessage());
                        }
                    }
                });
    }
}