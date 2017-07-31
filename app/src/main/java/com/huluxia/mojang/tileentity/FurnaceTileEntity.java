package com.huluxia.mojang.tileentity;

public class FurnaceTileEntity extends ContainerTileEntity {
    private short burnTime = (short) 0;
    private short cookTime = (short) 0;

    public short getBurnTime() {
        return this.burnTime;
    }

    public int getContainerSize() {
        return 3;
    }

    public short getCookTime() {
        return this.cookTime;
    }

    public void setBurnTime(short paramShort) {
        this.burnTime = paramShort;
    }

    public void setCookTime(short paramShort) {
        this.cookTime = paramShort;
    }
}
