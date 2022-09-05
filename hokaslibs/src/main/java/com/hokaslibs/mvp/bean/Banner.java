package com.hokaslibs.mvp.bean;

import java.io.Serializable;

public class Banner implements Serializable {
    /**
     * id : 2
     * resourceUrl : http://192.168.105.235/images/2017/10/9/b9868313-0c14-4dd7-b0d4-099ab685991d_ios.png
     * createTimeStamp : 1507519994000
     * updateTimeStamp : 1507520401000
     * creatorName : xiaoming
     * creatorId : 1
     * bannerType : 1
     * link : http://www.baidu.com
     */

    private int id;
    private String resourceUrl;
    private String creator;
    private int creatorId;
    private int bannerType;
    private String link;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId=" + creatorId +
                ", bannerType=" + bannerType +
                ", link='" + link + '\'' +
                ", sort=" + sort +
                '}';
    }
}
