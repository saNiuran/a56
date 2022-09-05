package com.niule.a56.calculator.mvp.model;

import com.niule.a56.calculator.base.BaseModel;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.mvp.contract.FreightContract;
import okhttp3.RequestBody;
import rx.Observable;

import java.util.List;

public class FreightModel extends BaseModel implements FreightContract.Model {

    @Override
    public Observable<BaseObject<List<ContinentPack>>> getContinentPackList() {
        return mServiceManager.getContinentPackList();
    }

    @Override
    public Observable<BaseObject<List<FreightPack>>> getFreightPackList(RequestBody body) {
        return mServiceManager.getFreightPackList(body);
    }

    @Override
    public Observable<BaseObject<DivisionPack>> getDivisionPack(RequestBody body) {
        return mServiceManager.getDivisionPack(body);
    }

    @Override
    public Observable<BaseObject<PriceRow>> validPriceRow(RequestBody body) {
        return mServiceManager.validPriceRow(body);
    }
}