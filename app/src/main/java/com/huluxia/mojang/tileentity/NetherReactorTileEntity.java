package com.huluxia.mojang.tileentity;

public class NetherReactorTileEntity extends TileEntity {
    private boolean hasFinished = false;
    private boolean isInitialized = false;
    private short progress = (short) 0;

    public short getProgress() {
        return this.progress;
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }

    public void setFinished(boolean paramBoolean) {
        this.hasFinished = paramBoolean;
    }

    public void setInitialized(boolean paramBoolean) {
        this.isInitialized = paramBoolean;
    }

    public void setProgress(short paramShort) {
        this.progress = paramShort;
    }
}
