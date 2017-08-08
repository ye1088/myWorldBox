package com.MCWorld.widget.dialog;

/* compiled from: MenuItem */
public class o {
    private int bxA = 0;
    private int bxB = 1;
    private int bxz = 0;
    private Object tag;
    private String title;

    public o(Object tag, String title) {
        this.title = title;
        this.tag = tag;
    }

    public o(Object tag, String title, int check) {
        this.title = title;
        this.tag = tag;
        this.bxA = check;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int Os() {
        return this.bxz;
    }

    public int On() {
        return this.bxA;
    }

    public void lV(int imageId) {
        this.bxz = imageId;
    }

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void setChecked(int checked) {
        this.bxA = checked;
    }
}
