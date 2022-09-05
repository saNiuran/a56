package com.niule.a56.calculator.bean;

public class PriceExtra {
    private long id;

    private long lordId;

    private int lordType;

    private String name;

    private int way;

    private int price;

    private int rate;

    private int status;

    private String unitName;

    private int okToSum;

    private int minChargePerBill;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLordId() {
        return lordId;
    }

    public void setLordId(long lordId) {
        this.lordId = lordId;
    }

    public int getLordType() {
        return lordType;
    }

    public void setLordType(int lordType) {
        this.lordType = lordType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public int getOkToSum() {
        return okToSum;
    }

    public void setOkToSum(int okToSum) {
        this.okToSum = okToSum;
    }

    public int getMinChargePerBill() {
        return minChargePerBill;
    }

    public void setMinChargePerBill(int minChargePerBill) {
        this.minChargePerBill = minChargePerBill;
    }
}