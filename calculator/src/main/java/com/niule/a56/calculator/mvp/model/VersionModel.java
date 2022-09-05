package com.niule.a56.calculator.mvp.model;

import com.niule.a56.calculator.base.BaseModel;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.mvp.contract.VersionContract;

import com.niule.a56.calculator.utils.update.manager.AppVersion;
import okhttp3.RequestBody;
import rx.Observable;

public class VersionModel extends BaseModel implements VersionContract.Model {


    @Override
    public Observable<BaseObject<AppVersion>> getLastApkVersion(RequestBody requestBody) {
        return mServiceManager.getLastApkVersion(requestBody);
    }
}