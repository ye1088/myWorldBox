package com.MCWorld.mojang.tileentity;

import com.MCWorld.mojang.util.Vector3f;
import java.io.Serializable;

public class TileEntity implements Serializable {
    private String id = null;
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public double distanceSquaredTo(Vector3f paramVector3f) {
        return (Math.pow((double) (paramVector3f.x - ((float) this.x)), 2.0d) + Math.pow((double) (paramVector3f.y - ((float) this.y)), 2.0d)) + Math.pow((double) (paramVector3f.z - ((float) this.z)), 2.0d);
    }

    public String getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void setId(String paramString) {
        this.id = paramString;
    }

    public void setX(int paramInt) {
        this.x = paramInt;
    }

    public void setY(int paramInt) {
        this.y = paramInt;
    }

    public void setZ(int paramInt) {
        this.z = paramInt;
    }

    public String toString() {
        return this.id + ": X: " + this.x + " Y: " + this.y + " Z: " + this.z;
    }
}
