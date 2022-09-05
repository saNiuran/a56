package com.niule.a56.calculator.bean;

import com.github.gzuliyujiang.wheelview.contract.TextProvider;

public class Continent implements TextProvider {
    private int id;

    private String name;

    private String sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    @Override
    public String provideText() {
        return getName();
    }
}