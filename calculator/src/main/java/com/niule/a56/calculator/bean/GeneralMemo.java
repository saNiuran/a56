package com.niule.a56.calculator.bean;

public class GeneralMemo {
    private long id;

    private long lordId;

    private int lordType;

    private String memo;

    private int status;

    private String sort;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
