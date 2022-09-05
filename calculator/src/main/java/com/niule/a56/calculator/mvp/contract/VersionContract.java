package com.niule.a56.calculator.mvp.contract;

import com.niule.a56.calculator.base.BaseView;
import com.niule.a56.calculator.base.BaseObject;

import com.niule.a56.calculator.utils.update.manager.AppVersion;
import okhttp3.RequestBody;
import rx.Observable;

public interface VersionContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseView {
        void onVersion(AppVersion bean);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model {
        Observable<BaseObject<AppVersion>> getLastApkVersion(RequestBody requestBody);
    }
}