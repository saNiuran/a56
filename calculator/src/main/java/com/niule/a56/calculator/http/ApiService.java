package com.niule.a56.calculator.http;


import com.hokaslibs.mvp.bean.*;

import java.util.List;

import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.utils.update.manager.AppVersion;
import com.niule.a56.calculator.base.BaseObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;


/**
 * 网络请求接口
 */
public interface ApiService {

//    @POST("smsapi/smsController/getSmsCode")
//    Observable<BaseObject<String>> getSmsVerifyCode(@Body RequestBody requestBody);

    /**
     * 首页轮播
     */
    @POST("commonapi/user/banner")
    Observable<BaseObject<List<Banner>>> getBanners(@Body RequestBody requestBody);

    /**
     * 文件上传
     */
    @Multipart
    @POST("upload/cargo/photo")
    Observable<BaseObject<String>> upLoadFile(@Part MultipartBody.Part file);

    /**
     * 公告
     */
    @GET("commonapi/user/notice")
    Observable<BaseObject<List<Notice>>> getNoticesList();

    /**
     * 版本更新
     */
    @POST("android/apk/version/last")
    Observable<BaseObject<AppVersion>> getLastApkVersion(@Body RequestBody requestBody);


    /**
     * 获取洲和国家 选择器预设数据
     */
    @POST("country/continent/mpvue/picker")
    Observable<BaseObject<List<ContinentPack>>> getContinentPackList();

    /**
     * 获取对应的国家已经开通的航线
     */
    @POST("freight/mpvue/picker")
    Observable<BaseObject<List<FreightPack>>> getFreightPackList(@Body RequestBody requestBody);

    /**
     * 获取对应的航线的分区信息
     */
    @POST("division/pack2")
    Observable<BaseObject<DivisionPack>> getDivisionPack(@Body RequestBody body);

    /**
     * 根据航线id和分区id和货物id 查找是否有价格
     */
    @POST("price/row/valid")
    Observable<BaseObject<PriceRow>> validPriceRow(@Body RequestBody body);

    /**
     * 获取计价单位列表
     */
    @POST("charge/unit/list/all")
    Observable<BaseObject<List<ChargeUnit>>> getChargeUnitList();

    /**
     * 获取 价格明细
     */
    @POST("price/item/pack")
    Observable<BaseObject<PriceItemPack>> getPriceItemPack(@Body RequestBody body);
}
