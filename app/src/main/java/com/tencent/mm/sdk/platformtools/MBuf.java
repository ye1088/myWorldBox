package com.tencent.mm.sdk.platformtools;

import java.nio.ByteBuffer;

class MBuf {
    private ByteBuffer al = null;

    MBuf() {
    }

    public ByteBuffer getBuffer() {
        return this.al;
    }

    public int getLen() {
        return this.al.capacity();
    }

    public int getOffset() {
        return this.al.position();
    }

    public void setBuffer(byte[] bArr) {
        int length = bArr.length;
        this.al = ByteBuffer.allocateDirect(length);
        this.al.position(0);
        this.al.put(bArr, 0, length);
        this.al.position(0);
    }
}
