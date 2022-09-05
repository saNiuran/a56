package com.hokaslibs.mvp.bean;

import java.io.Serializable;

/**
 * @Author Rich on 2020-03-10 13:53
 * @Projcet cat
 */
public class Notice implements Serializable {
    private int id;
    private int typeId;
    private String content;
    private String isstart;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsstart() {
        return isstart;
    }

    public void setIsstart(String isstart) {
        this.isstart = isstart;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", content='" + content + '\'' +
                ", isstart='" + isstart + '\'' +
                '}';
    }
}
