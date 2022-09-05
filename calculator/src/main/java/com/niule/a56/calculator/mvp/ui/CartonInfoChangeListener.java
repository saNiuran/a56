package com.niule.a56.calculator.mvp.ui;

import com.niule.a56.calculator.bean.CartonInfo;

public interface CartonInfoChangeListener {
    void onCartonInfoChanged(CartonInfo cartonInfo,int position);
    void onRemoveCarton(int position);
}
