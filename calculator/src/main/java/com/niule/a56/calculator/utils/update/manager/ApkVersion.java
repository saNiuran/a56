package com.niule.a56.calculator.utils.update.manager;

public class ApkVersion {
    private int id;
    private String apkUrl;
    private String versionCode;
    private String versionName;
    private String content;
    private int digit;  //'0=32位 1=64位'
    private int archit;  //'0=arm 1=intel 2=misc'
    private int  updateForce;   // 0 不强制更新  1 强制更新
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public int getArchit() {
        return archit;
    }

    public void setArchit(int archit) {
        this.archit = archit;
    }

    public int getUpdateForce() {
        return updateForce;
    }

    public void setUpdateForce(int updateForce) {
        this.updateForce = updateForce;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
