package com.niule.a56.calculator.bean;

import java.io.Serializable;
import java.util.List;

public class PriceItemPack extends PriceItem implements Serializable {
    private PriceRow priceRow;
    private List<PriceExtra> priceExtraList;
    private Integer smallPack;

    public PriceRow getPriceRow() {
        return priceRow;
    }

    public void setPriceRow(PriceRow priceRow) {
        this.priceRow = priceRow;
    }

    public List<PriceExtra> getPriceExtraList() {
        return priceExtraList;
    }

    public void setPriceExtraList(List<PriceExtra> priceExtraList) {
        this.priceExtraList = priceExtraList;
    }

    public Integer getSmallPack() {
        return smallPack;
    }

    public void setSmallPack(Integer smallPack) {
        this.smallPack = smallPack;
    }
}
