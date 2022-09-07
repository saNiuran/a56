package com.niule.a56.calculator.bean;

import com.niule.a56.calculator.utils.update.manager.ApkVersion;

import java.io.Serializable;

public class ApkVersionRequest extends ApkVersion implements Serializable {
    private UserAn userAn;

    public UserAn getUserAn() {
        return userAn;
    }

    public void setUserAn(UserAn userAn) {
        this.userAn = userAn;
    }
}
