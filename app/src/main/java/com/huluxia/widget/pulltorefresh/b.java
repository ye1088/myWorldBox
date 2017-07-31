package com.huluxia.widget.pulltorefresh;

/* compiled from: TouchTool */
public class b {
    private int bEX;
    private int bEY;
    private int bEZ;
    private int bFa;

    public b(int startX, int startY, int endX, int endY) {
        this.bEX = startX;
        this.bEY = startY;
        this.bEZ = endX;
        this.bFa = endY;
    }

    public int P(float dx) {
        return (int) (((float) this.bEX) + (dx / 2.5f));
    }

    public int Q(float dy) {
        return (int) (((float) this.bEY) + (dy / 2.5f));
    }
}
