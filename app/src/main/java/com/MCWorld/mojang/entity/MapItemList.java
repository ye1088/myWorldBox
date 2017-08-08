package com.MCWorld.mojang.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class MapItemList implements Serializable {
    private ArrayList<MapResourceItem> items;
    private String updateDate;

    public ArrayList<MapResourceItem> getItems() {
        Collections.sort(this.items);
        return this.items;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setItems(ArrayList<MapResourceItem> paramArrayList) {
        this.items = paramArrayList;
    }

    public void setUpdateDate(String paramString) {
        this.updateDate = paramString;
    }
}
