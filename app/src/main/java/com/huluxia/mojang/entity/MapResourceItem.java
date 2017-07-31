package com.huluxia.mojang.entity;

import java.io.Serializable;

public class MapResourceItem implements Serializable, Comparable<MapResourceItem> {
    private String briefDesc;
    private String detail;
    private String downloadUrl;
    private int id;
    private String image;
    private Integer size;
    private String title;
    private String type;

    public MapResourceItem(int paramInt, String paramString1, Integer paramInteger, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
        this.id = paramInt;
        this.title = paramString1;
        this.size = paramInteger;
        this.type = paramString2;
        this.briefDesc = paramString3;
        this.detail = paramString4;
        this.image = paramString5;
        this.downloadUrl = paramString6;
    }

    public int compareTo(MapResourceItem paramMapResourceItem) {
        return paramMapResourceItem.getId() - getId();
    }

    public String getBriefDesc() {
        return this.briefDesc;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public int getId() {
        return this.id;
    }

    public String getImage() {
        return this.image;
    }

    public Integer getSize() {
        return this.size;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public void setBriefDesc(String paramString) {
        this.briefDesc = paramString;
    }

    public void setDetail(String paramString) {
        this.detail = paramString;
    }

    public void setDownloadUrl(String paramString) {
        this.downloadUrl = paramString;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }

    public void setImage(String paramString) {
        this.image = paramString;
    }

    public void setSize(Integer paramInteger) {
        this.size = paramInteger;
    }

    public void setTitle(String paramString) {
        this.title = paramString;
    }

    public void setType(String paramString) {
        this.type = paramString;
    }
}
