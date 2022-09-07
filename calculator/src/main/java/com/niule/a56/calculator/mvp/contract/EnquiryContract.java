package com.niule.a56.calculator.mvp.contract;

import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.base.BaseView;
import com.niule.a56.calculator.bean.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import rx.Observable;

import java.util.List;

public interface EnquiryContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseView {
        void onContinentPackData(List<ContinentPack> list);

        void onUpImgDone(List<ImagePath> list);

        void onCreateEnquirySuccess(Enquiry enquiry);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model {
        /**
         * 获取国家和洲的预设值
         */
        Observable<BaseObject<List<ContinentPack>>> getContinentPackList();

        /**
         * 上传图片
         */
        Observable<BaseObject<List<ImagePath>>> upLoadImage(MultipartBody.Part part);

        /**
         * 创建询盘
         */
        Observable<BaseObject<Enquiry>> createEnquiry(RequestBody body);

    }
}