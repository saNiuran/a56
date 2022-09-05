package com.niule.a56.calculator.bean;

import com.github.gzuliyujiang.wheelview.contract.TextProvider;

public class Division implements TextProvider {
    private long id;

    private String name;

    private long freightLineId;

    private int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFreightLineId() {
        return freightLineId;
    }

    public void setFreightLineId(long freightLineId) {
        this.freightLineId = freightLineId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String provideText() {
        return getName();
    }
}