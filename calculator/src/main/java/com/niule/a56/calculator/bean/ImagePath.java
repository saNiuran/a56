package com.niule.a56.calculator.bean;

import java.io.Serializable;

/**
 * @Author Rich on 2020-02-16 16:35
 * @Projcet cat
 */
public class ImagePath implements Serializable {
    private String localPath;
    private String webPath;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    @Override
    public String toString() {
        return "ImagePath{" +
                "localPath='" + localPath + '\'' +
                ", webPath='" + webPath + '\'' +
                '}';
    }
}
