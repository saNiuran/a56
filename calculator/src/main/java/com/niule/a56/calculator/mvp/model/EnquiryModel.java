package com.niule.a56.calculator.mvp.model;

import com.niule.a56.calculator.base.BaseModel;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.bean.ContinentPack;
import com.niule.a56.calculator.bean.Enquiry;
import com.niule.a56.calculator.bean.ImagePath;
import com.niule.a56.calculator.mvp.contract.EnquiryContract;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import rx.Observable;

import java.util.List;

public class EnquiryModel extends BaseModel implements EnquiryContract.Model {

    @Override
    public Observable<BaseObject<List<ContinentPack>>> getContinentPackList() {
        return mServiceManager.getContinentPackList();
    }

    @Override
    public Observable<BaseObject<List<ImagePath>>> upLoadImage(MultipartBody.Part part) {
        return mServiceManager.upLoadImage(part);
    }

    @Override
    public Observable<BaseObject<Enquiry>> createEnquiry(RequestBody body) {
        return mServiceManager.createEnquiry(body);
    }
}