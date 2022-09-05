package com.niule.a56.calculator.bean;

import java.util.Date;

public class ApkVersion {
    private long id;

    private Date createTime;

    private Date updateTime;

    private String apkUrl;

    private String versionCode;

    private String versionName;

    private String content;

    private Integer updateForce;

    private Integer digit;

    private Integer archit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl == null ? null : apkUrl.trim();
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode == null ? null : versionCode.trim();
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUpdateForce() {
        return updateForce;
    }

    public void setUpdateForce(Integer updateForce) {
        this.updateForce = updateForce;
    }

    public Integer getDigit() {
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    public Integer getArchit() {
        return archit;
    }

    public void setArchit(Integer archit) {
        this.archit = archit;
    }
}