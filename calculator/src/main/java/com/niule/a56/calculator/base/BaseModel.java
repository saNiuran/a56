package com.niule.a56.calculator.base;


import com.niule.a56.calculator.http.Api;
import com.niule.a56.calculator.http.ApiService;

/**
 * 作者： Hokas
 * 时间： 2017/1/5
 * 类别：
 */

public class BaseModel {
    protected ApiService mServiceManager;//服务管理类,用于网络请求
//    protected ApiCache mCacheManager;//缓存管理类,用于管理本地或者内存缓存

    public BaseModel() {
        this.mServiceManager = Api.getApiService();
//        this.mCacheManager = Api.getApiCache();
    }


    public void onDestroy() {
        if (mServiceManager != null) {
            mServiceManager = null;
        }
//        if (mCacheManager != null) {
//            mCacheManager = null;
//        }
    }
}
