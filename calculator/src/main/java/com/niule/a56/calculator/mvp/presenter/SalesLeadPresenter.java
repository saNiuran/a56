package com.niule.a56.calculator.mvp.presenter;

import android.content.Context;
import com.google.gson.Gson;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.base.BasePresenter;
import com.niule.a56.calculator.bean.QuestionTemplate;
import com.niule.a56.calculator.bean.SalesLead;
import com.niule.a56.calculator.bean.SalesLeadRequest;
import com.niule.a56.calculator.http.XApi;
import com.niule.a56.calculator.mvp.contract.SalesLeadContract;
import com.niule.a56.calculator.mvp.model.SalesLeadModel;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.RequestBody;

import java.util.List;

public class SalesLeadPresenter extends BasePresenter<SalesLeadContract.Model, SalesLeadContract.View> {

    public SalesLeadPresenter(Context context, SalesLeadContract.View view) {
        super(new SalesLeadModel(), view, context);
    }

    @Override
    public void handleResponseError(Context context, Exception e) {
        super.handleResponseError(context, e);
    }

    /**
     * 获取 留言分类标题列表
     */
    public void getQuestionTemplateAll() {
        mModel.getQuestionTemplateAll()
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<List<QuestionTemplate>>>getApiTransformer())
                .compose(XApi.<BaseObject<List<QuestionTemplate>>>getScheduler())
                .compose(BasePresenter.<BaseObject<List<QuestionTemplate>>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<List<QuestionTemplate>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<List<QuestionTemplate>> baseObject) {
                        if (0 == baseObject.getRetCode()) {
                            mRootView.onQuestionTemplateData(baseObject.getData());
                        } else {
                            if (baseObject.getMessage() != null && !baseObject.getMessage().trim().isEmpty()) {
                                mRootView.showMessage(baseObject.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 创建销售线索
     */
    public void createSalesLead(SalesLeadRequest salesLead) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(salesLead));
        mModel.createSalesLead(body)
                .retryWhen(new RetryWithDelay(2, 10))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(XApi.<BaseObject<SalesLead>>getApiTransformer())
                .compose(XApi.<BaseObject<SalesLead>>getScheduler())
                .compose(BasePresenter.<BaseObject<SalesLead>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseObject<SalesLead>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseObject<SalesLead> baseObject) {
                        if (0 == baseObject.getRetCode()) {
                            mRootView.onCreateSalesLeadSuccess();
                        } else {
                            if (baseObject.getMessage() != null && !baseObject.getMessage().trim().isEmpty()) {
                                mRootView.showMessage(baseObject.getMessage());
                            }
                        }
                    }
                });
    }

}