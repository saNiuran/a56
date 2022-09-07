package com.niule.a56.calculator.mvp.model;

import com.niule.a56.calculator.base.BaseModel;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.mvp.contract.FreightContract;
import com.niule.a56.calculator.mvp.contract.PriceContract;
import okhttp3.RequestBody;
import rx.Observable;

import java.util.List;

public class PriceModel extends BaseModel implements PriceContract.Model {

    @Override
    public Observable<BaseObject<List<ChargeUnit>>> getChargeUnitList() {
        return mServiceManager.getChargeUnitList();
    }

    @Override
    public Observable<BaseObject<PriceItemPack>> getPriceItemPack(RequestBody body) {
        return mServiceManager.getPriceItemPack(body);
    }

    @Override
    public Observable<BaseObject<List<GeneralMemo>>> getGeneralMemoCombo(RequestBody body) {
        return mServiceManager.getGeneralMemoCombo(body);
    }
}