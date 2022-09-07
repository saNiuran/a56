package com.niule.a56.calculator.mvp.contract;

import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.base.BaseView;
import com.niule.a56.calculator.bean.QuestionTemplate;
import com.niule.a56.calculator.bean.SalesLead;
import okhttp3.RequestBody;
import rx.Observable;

import java.util.List;

public interface SalesLeadContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseView {
        void onQuestionTemplateData(List<QuestionTemplate> list);
        void onCreateSalesLeadSuccess();
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model {
        Observable<BaseObject<List<QuestionTemplate>>> getQuestionTemplateAll();

        Observable<BaseObject<SalesLead>> createSalesLead(RequestBody body);
    }
}