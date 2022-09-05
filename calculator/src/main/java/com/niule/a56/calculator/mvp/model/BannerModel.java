package com.niule.a56.calculator.mvp.model;

import com.niule.a56.calculator.base.BaseModel;
import com.hokaslibs.mvp.bean.Banner;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.mvp.contract.BannerContract;

import okhttp3.RequestBody;
import rx.Observable;

import java.util.List;

public class BannerModel extends BaseModel implements BannerContract.Model {


    @Override
    public Observable<BaseObject<List<Banner>>> getBanners(RequestBody requestBody) {
        return mServiceManager.getBanners(requestBody);
    }
}