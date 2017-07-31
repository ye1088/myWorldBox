package com.huluxia.mojang.converter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DBKey {
    public static final int CHUNK = 48;
    public static final int ENTITY = 50;
    public static final int PLACEHOLDER = 118;
    public static final int TILE_ENTITY = 49;
    private int type;
    private int x;
    private int z;

    public DBKey() {
        this(0, 0, 0);
    }

    public DBKey(int paramInt1, int paramInt2, int paramInt3) {
        this.x = paramInt1;
        this.z = paramInt2;
        this.type = paramInt3;
    }

    public DBKey(DBKey paramDBKey) {
        this(paramDBKey.x, paramDBKey.z, paramDBKey.type);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DBKey dbKey = (DBKey) o;
        if (this.type != dbKey.type) {
            return false;
        }
        if (this.x != dbKey.x) {
            return false;
        }
        if (this.z != dbKey.z) {
            return false;
        }
        return true;
    }

    public void fromBytes(byte[] paramArrayOfByte) {
        this.x = ((paramArrayOfByte[0] | (paramArrayOfByte[1] << 8)) | (paramArrayOfByte[2] << 16)) | (paramArrayOfByte[3] << 24);
        this.z = ((paramArrayOfByte[4] | (paramArrayOfByte[5] << 8)) | (paramArrayOfByte[6] << 16)) | (paramArrayOfByte[7] << 24);
        this.type = paramArrayOfByte[8] & 255;
    }

    public int getType() {
        return this.type;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public int hashCode() {
        return ((((this.x + 31) * 31) + this.z) * 31) + this.type;
    }

    public DBKey setType(int paramInt) {
        this.type = paramInt;
        return this;
    }

    public DBKey setX(int paramInt) {
        this.x = paramInt;
        return this;
    }

    public DBKey setZ(int paramInt) {
        this.z = paramInt;
        return this;
    }

    public byte[] toBytes() {
        try {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(9);
            DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
            localDataOutputStream.writeInt(Integer.reverseBytes(this.x));
            localDataOutputStream.writeInt(Integer.reverseBytes(this.z));
            localDataOutputStream.writeByte(this.type);
            return localByteArrayOutputStream.toByteArray();
        } catch (IOException localIOException) {
            throw new RuntimeException(localIOException);
        }
    }

    public String toString() {
        return super.getClass().getSimpleName() + ": " + this.x + "_" + this.z + "_" + this.type;
    }
}
