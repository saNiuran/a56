package com.niule.a56.calculator.bean;

import java.io.Serializable;

public class SalesLeadRequest extends SalesLead implements Serializable {
    private String mobile;
    private String name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
