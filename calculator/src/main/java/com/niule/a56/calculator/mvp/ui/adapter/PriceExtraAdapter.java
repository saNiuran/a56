package com.niule.a56.calculator.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hokaslibs.utils.HokasUtils;
import com.hokaslibs.utils.recycler.XRecyclerAdapter;
import com.hokaslibs.utils.recycler.XRecyclerViewHolder;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.bean.CartonInfo;
import com.niule.a56.calculator.bean.PriceExtra;
import com.niule.a56.calculator.mvp.ui.CartonInfoChangeListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceExtraAdapter extends XRecyclerAdapter<PriceExtra> {

    private String chargeUnitName;

    public PriceExtraAdapter(Context context, int layoutId, List<PriceExtra> priceExtraList) {
        super(context, layoutId, priceExtraList);
    }

    @Override
    public void convert(XRecyclerViewHolder holder, PriceExtra priceExtra, int position) {
        if(holder!=null && priceExtra!=null){
            TextView tvItemStar = holder.getConvertView().findViewById(R.id.tvItemStar);
            if(priceExtra.getOkToSum()>0){
                tvItemStar.setVisibility(View.GONE);
            }else{
                tvItemStar.setVisibility(View.VISIBLE);
            }

            TextView tvItemPriceExtraName = holder.getConvertView().findViewById(R.id.tvItemPriceExtraName);
            tvItemPriceExtraName.setText(priceExtra.getName());

            TextView tvItemPrice = holder.getConvertView().findViewById(R.id.tvItemPrice);
            if(priceExtra.getWay()==2) {
                tvItemPrice.setText(HokasUtils.toMoney(priceExtra.getPrice())+"%");
            }else{
                tvItemPrice.setText("￥" + HokasUtils.toMoney(priceExtra.getPrice()));
                TextView tvItemPriceUnit = holder.getConvertView().findViewById(R.id.tvItemPriceUnit);
                if(priceExtra.getWay()==0){
                    tvItemPriceUnit.setText("/"+chargeUnitName);
                }else{
                    tvItemPriceUnit.setText("/"+priceExtra.getUnitName());
                }
            }
        }
    }

    public void setChargeUnitName(String chargeUnitName){
        this.chargeUnitName = chargeUnitName;
    }
}
