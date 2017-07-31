package com.huluxia.mojang.entity;

import java.io.Serializable;
import java.util.List;

public class MapResource implements Serializable {
    private List<MapItemList> data;
    private String lastUpdate;
    private String maxVersion;
    private String minVersion;

    public List<MapItemList> getData() {
        return this.data;
    }

    public String getLastUpdate() {
        return this.lastUpdate;
    }

    public String getMaxVersion() {
        return this.maxVersion;
    }

    public String getMinVersion() {
        return this.minVersion;
    }

    public void setData(List<MapItemList> paramList) {
        this.data = paramList;
    }

    public void setLastUpdate(String paramString) {
        this.lastUpdate = paramString;
    }

    public void setMaxVersion(String paramString) {
        this.maxVersion = paramString;
    }

    public void setMinVersion(String paramString) {
        this.minVersion = paramString;
    }
}
