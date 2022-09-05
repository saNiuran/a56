package com.niule.a56.calculator.mvp.contract;

import com.niule.a56.calculator.base.BaseView;
import com.hokaslibs.mvp.bean.Banner;

import java.util.List;

import com.niule.a56.calculator.base.BaseObject;
import okhttp3.RequestBody;
import rx.Observable;

public interface BannerContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseView {
        void onBannerList(List<Banner> bannerList);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model {
        /**
         * 轮播
         */
        Observable<BaseObject<List<Banner>>> getBanners(RequestBody requestBody);
    }
}