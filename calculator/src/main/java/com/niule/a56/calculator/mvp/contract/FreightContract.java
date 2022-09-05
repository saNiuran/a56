package com.niule.a56.calculator.mvp.contract;

import com.niule.a56.calculator.base.BaseView;
import com.niule.a56.calculator.base.BaseObject;
import com.niule.a56.calculator.bean.*;
import okhttp3.RequestBody;
import rx.Completable;
import rx.Observable;

import java.util.List;

public interface FreightContract {
    interface View extends BaseView {
        void onContinentPackData(List<ContinentPack> continentList);
        void onFreightPackData(List<FreightPack> freightPackList);
        void onFreightPackError();

        void onDivisionPackData(DivisionPack divisionPack);

        void onPriceRowData(PriceRow priceRow);
    }

    interface Model {
        /**
         * 获取国家和洲的预设值
         */
        Observable<BaseObject<List<ContinentPack>>> getContinentPackList();

        /**
         * 获取国家已经开通的航线
         */
        Observable<BaseObject<List<FreightPack>>> getFreightPackList(RequestBody body);

        /**
         * 获取航线对应的分区
         */
        Observable<BaseObject<DivisionPack>> getDivisionPack(RequestBody body);

        /**
         * 获取航线对应的分区
         */
        Observable<BaseObject<PriceRow>> validPriceRow(RequestBody body);

    }
}