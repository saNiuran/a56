package com.niule.a56.calculator.bean;

public class PriceItem {
    private long id;

    private long priceRowId;

    private int weight;

    private int price;

    private int direction;

    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPriceRowId() {
        return priceRowId;
    }

    public void setPriceRowId(long priceRowId) {
        this.priceRowId = priceRowId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}