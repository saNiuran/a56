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
import com.niule.a56.calculator.mvp.ui.CartonInfoChangeListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CartonAdapter extends XRecyclerAdapter<CartonInfo> {

    public CartonAdapter(Context context, int layoutId, List<CartonInfo> cartonInfoList) {
        super(context, layoutId, cartonInfoList);
    }

    @Override
    public void convert(XRecyclerViewHolder holder, CartonInfo cartonInfo, int position) {
        if(holder!=null && cartonInfo!=null){
            if(cartonInfo.getLength()>0) {
                holder.setText(R.id.etItemLength, cartonInfo.getLength() + "");
            }else{
                holder.setText(R.id.etItemLength,"");
            }

            EditText etItemLength = holder.getConvertView().findViewById(R.id.etItemLength);
            etItemLength.setText(cartonInfo.getLength()>0?cartonInfo.getLength()+"":"");
            etItemLength.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        cartonInfo.setLength(toFloat(etItemLength.getText().toString(),0));
                        cartonListener.onCartonInfoChanged(cartonInfo,position);
                    }
                }
            });

            EditText etItemWidth = holder.getConvertView().findViewById(R.id.etItemWidth);
            etItemWidth.setText(cartonInfo.getWidth()>0?cartonInfo.getWidth()+"":"");
            etItemWidth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        cartonInfo.setWidth(toFloat(etItemWidth.getText().toString(),0));
                        cartonListener.onCartonInfoChanged(cartonInfo,position);
                    }
                }
            });

            EditText etItemHeight = holder.getConvertView().findViewById(R.id.etItemHeight);
            etItemHeight.setText(cartonInfo.getHeight()>0?cartonInfo.getHeight()+"":"");
            etItemHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        cartonInfo.setHeight(toFloat(etItemHeight.getText().toString(),0));
                        cartonListener.onCartonInfoChanged(cartonInfo,position);
                    }
                }
            });

            EditText etItemGrossWeight = holder.getConvertView().findViewById(R.id.etItemGrossWeight);
            etItemGrossWeight.setText(cartonInfo.getWeight()>0?cartonInfo.getWeight()+"":"");
            etItemGrossWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        cartonInfo.setWeight(toFloat(etItemGrossWeight.getText().toString(),1));
                        cartonListener.onCartonInfoChanged(cartonInfo,position);
                    }
                }
            });

            EditText etItemCartons = holder.getConvertView().findViewById(R.id.etItemCartons);
            etItemCartons.setText(cartonInfo.getQuantity()+"");

            TextView tvItemIncrease = holder.getConvertView().findViewById(R.id.tvItemIncrease);
            tvItemIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = etItemCartons.getText().toString();
                    int value = 1;
                    if(s!=null && !s.equals("")){
                        value = Integer.parseInt(s);
                    }
                    etItemCartons.setText((value+1)+"");
                    cartonInfo.setQuantity(value+1);
                    cartonListener.onCartonInfoChanged(cartonInfo,position);
                }
            });

            etItemCartons.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        String s = etItemCartons.getText().toString();
                        int value = 1;
                        if(s!=null && !s.equals("")){
                            value = Integer.parseInt(s);
                        }
                        cartonInfo.setQuantity(value);
                        cartonListener.onCartonInfoChanged(cartonInfo,position);
                    }
                }
            });

            TextView tvItemReduce = holder.getConvertView().findViewById(R.id.tvItemReduce);
            tvItemReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = etItemCartons.getText().toString();
                    int value = 1;
                    if(s!=null && !s.equals("")){
                        value = Integer.parseInt(s);
                    }
                    value = value - 1;
                    if(value<1){
                        value = 1;
                    }
                    etItemCartons.setText(value+"");
                    cartonInfo.setQuantity(value);
                    cartonListener.onCartonInfoChanged(cartonInfo,position);
                }
            });

            LinearLayout llItemDelete = holder.getConvertView().findViewById(R.id.llItemDelete);
            llItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartonListener.onRemoveCarton(position);
                }
            });
        }
    }

    CartonInfoChangeListener cartonListener;

    public void setCartonListener(CartonInfoChangeListener cartonListener){
        this.cartonListener = cartonListener;
    }

    private float toFloat(String s, int scale){
        if(HokasUtils.isNoEmpty(s)) {
            return BigDecimal.valueOf(Float.parseFloat(s)).setScale(scale, RoundingMode.HALF_UP).floatValue();
        }else{
            return 0;
        }
    }
}
