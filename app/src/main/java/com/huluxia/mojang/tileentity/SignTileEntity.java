package com.huluxia.mojang.tileentity;

import java.util.Arrays;

public class SignTileEntity extends TileEntity {
    public static final int NUM_LINES = 4;
    private String[] lines = new String[]{"", "", "", ""};

    public String getLine(int paramInt) {
        return this.lines[paramInt];
    }

    public String[] getLines() {
        return this.lines;
    }

    public void setLine(int paramInt, String paramString) {
        this.lines[paramInt] = paramString;
    }

    public String toString() {
        return super.toString() + Arrays.asList(this.lines).toString();
    }
}
