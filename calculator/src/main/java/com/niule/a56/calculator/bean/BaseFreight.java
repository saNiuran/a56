package com.niule.a56.calculator.bean;

import com.github.gzuliyujiang.wheelview.contract.TextProvider;

public class BaseFreight implements TextProvider {
    private long id;

    private String name;

    private Integer type;

    private String memo;

    private String sort;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String provideText() {
        return getName();
    }
}