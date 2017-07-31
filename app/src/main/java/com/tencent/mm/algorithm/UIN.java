package com.tencent.mm.algorithm;

public class UIN extends Number {
    private int h = 0;

    public UIN(int i) {
        this.h = i;
    }

    public UIN(long j) {
        this.h = (int) (-1 & j);
    }

    public static int valueOf(String str) {
        try {
            return new UIN(Long.valueOf(str).longValue()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public double doubleValue() {
        return ((double) (((long) this.h) | 0)) + 0.0d;
    }

    public float floatValue() {
        return (float) (((double) (((long) this.h) | 0)) + 0.0d);
    }

    public int intValue() {
        return this.h;
    }

    public long longValue() {
        return ((long) this.h) & 4294967295L;
    }

    public String toString() {
        return String.valueOf(((long) this.h) & 4294967295L);
    }

    public int value() {
        return this.h;
    }
}
