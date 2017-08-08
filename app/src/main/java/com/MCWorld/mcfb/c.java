package com.MCWorld.mcfb;

/* compiled from: MCFBMonsterItem */
public class c {
    public static final int OA = 2;
    public static final int OB = 3;
    public static final int OC = 4;
    public static final int OD = 5;
    public static final int OE = 6;
    public static final int OF = 7;
    public static final int OG = 8;
    public static final int OH = 9;
    public static final int OI = 10;
    public static final int OJ = 11;
    public static final int OL = 12;
    public static final int OM = 13;
    public static final int OO = 14;
    public static final int OP = 15;
    public static final int OQ = 16;
    public static final int OS = 17;
    public static final int OT = 18;
    public static final int OU = 19;
    public static final int OV = 20;
    public static final int OW = 21;
    public static final int OX = 22;
    public static final int OY = 23;
    public static final int OZ = 24;
    public static final int Oy = 0;
    public static final int Oz = 1;
    public static final int Pa = 25;
    public static final int Pb = 26;
    private int count;
    private int id;
    private int index;

    public c(int inputIndex, int inputId, int inputCount) {
        this.index = inputIndex;
        this.id = inputId;
        this.count = inputCount;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void qo() {
        this.count++;
    }

    public void qp() {
        this.count--;
    }
}
