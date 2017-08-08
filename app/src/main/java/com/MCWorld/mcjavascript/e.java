package com.MCWorld.mcjavascript;

/* compiled from: DTJSItem */
public class e {
    public static final int ajD = 0;
    public static final int ajE = 1;
    public static final int ajF = 2;
    boolean agK;
    int ajA;
    int ajB;
    boolean ajC;
    int encode;
    String name;
    String path;

    public e(String in_path, int in_encode, int in_enable) {
        this.path = in_path;
        this.name = this.path.substring(this.path.lastIndexOf("\\") + 1);
        System.out.println("fileName = " + this.name);
        this.encode = in_encode;
        this.ajA = in_enable;
        this.agK = false;
    }

    public e() {
        this.name = null;
        this.path = null;
        this.ajA = 0;
        this.encode = 0;
        this.agK = false;
    }

    public void a(String in_path, int in_encode, int in_enable, boolean in_valid, int in_tagIdx) {
        this.path = in_path;
        if (in_encode == 0) {
            this.name = this.path.substring(this.path.lastIndexOf("/") + 1);
            System.out.println("fileName = " + this.name);
        } else {
            this.name = in_path;
        }
        this.encode = in_encode;
        this.ajA = in_enable;
        this.ajB = in_tagIdx;
        this.agK = in_valid;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int Ah() {
        return this.encode;
    }

    public void hz(int encode) {
        this.encode = encode;
    }

    public int Ai() {
        return this.ajA;
    }

    public void hA(int enable) {
        this.ajA = enable;
    }

    public boolean isValid() {
        return this.agK;
    }

    public void bI(boolean valid) {
        this.agK = valid;
    }

    public int Aj() {
        return this.ajB;
    }

    public void hB(int tagIdx) {
        this.ajB = tagIdx;
    }

    public boolean Ak() {
        return this.ajC;
    }

    public void bW(boolean bEnabled) {
        this.ajC = bEnabled;
    }
}
