package com.huluxia.mojang.tileentity;

public class ChestTileEntity extends ContainerTileEntity {
    private int pairx = -65535;
    private int pairz = -65535;

    public int getPairX() {
        return this.pairx;
    }

    public int getPairZ() {
        return this.pairz;
    }

    public void setPairX(int paramInt) {
        this.pairx = paramInt;
    }

    public void setPairZ(int paramInt) {
        this.pairz = paramInt;
    }
}
