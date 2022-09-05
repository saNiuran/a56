package com.niule.a56.calculator.bean;

import java.io.Serializable;

/**
 * @Author Rich on 2020-12-20 13:26
 * @Projcet frog
 */
public class NotifyExtras implements Serializable {
    private String notifyCode;
    private String title;
    private String imgUrl;
    private String img;
    private String name;
    private String relationId;
    private String content;
    private String sound;
    private String userType;
    private String notificationTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(String notifyCode) {
        this.notifyCode = notifyCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "NotifyExtras{" +
                "notifyCode='" + notifyCode + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", relationId='" + relationId + '\'' +
                ", content='" + content + '\'' +
                ", sound='" + sound + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
