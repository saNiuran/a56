package com.niule.a56.calculator.mvp.contract;

import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.base.BaseView;
import com.niule.a56.calculator.bean.*;
import okhttp3.RequestBody;
import rx.Completable;
import rx.Observable;

import java.util.List;

public interface PriceContract {
    interface View extends BaseView {
        void onChargeUnitListData(List<ChargeUnit> list);
        void onPriceItemPackData(PriceItemPack pack);
    }

    interface Model {
        /**
         * 获取计价单位列表
         */
        Observable<BaseObject<List<ChargeUnit>>> getChargeUnitList();

        /**
         * 获取 价格明细
         */
        Observable<BaseObject<PriceItemPack>> getPriceItemPack(RequestBody body);

    }
}