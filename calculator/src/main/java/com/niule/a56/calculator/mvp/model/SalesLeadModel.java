package com.niule.a56.calculator.mvp.model;

import com.niule.a56.calculator.base.BaseModel;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.bean.QuestionTemplate;
import com.niule.a56.calculator.bean.SalesLead;
import com.niule.a56.calculator.mvp.contract.SalesLeadContract;
import okhttp3.RequestBody;
import rx.Observable;

import java.util.List;

public class SalesLeadModel extends BaseModel implements SalesLeadContract.Model {

    @Override
    public Observable<BaseObject<List<QuestionTemplate>>> getQuestionTemplateAll() {
        return mServiceManager.getQuestionTemplateAll();
    }

    @Override
    public Observable<BaseObject<SalesLead>> createSalesLead(RequestBody body) {
        return mServiceManager.createSalesLead(body);
    }

}