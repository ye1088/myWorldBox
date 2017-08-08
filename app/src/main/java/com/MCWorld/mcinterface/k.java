package com.MCWorld.mcinterface;

/* compiled from: MCPotionItem */
public class k {
    private int BP;
    private int YP;
    private int mID;
    private String mName;

    public k() {
        this.BP = 0;
        this.YP = 0;
        this.mID = 0;
    }

    public k(int id, int time, int level) {
        this.mID = id;
        this.YP = time;
        this.BP = level;
        this.mName = null;
    }

    public k(int id, int time, int level, String name) {
        this.mID = id;
        this.YP = time;
        this.BP = level;
        this.mName = name;
    }

    public int Ab() {
        return this.mID;
    }

    public void hx(int mID) {
        this.mID = mID;
    }

    public int getTime() {
        return this.YP;
    }

    public void setTime(int mTime) {
        this.YP = mTime;
    }

    public int getLevel() {
        return this.BP;
    }

    public void setLevel(int mLevel) {
        this.BP = mLevel;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
