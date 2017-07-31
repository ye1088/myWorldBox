package com.tencent.mm.sdk.platformtools;

import java.nio.ByteBuffer;

public class LVBuffer {
    public static final int LENGTH_ALLOC_PER_NEW = 4096;
    public static final int MAX_STRING_LENGTH = 2048;
    private ByteBuffer U;
    private boolean V;

    private int b(int i) {
        if (this.U.limit() - this.U.position() <= i) {
            ByteBuffer allocate = ByteBuffer.allocate(this.U.limit() + 4096);
            allocate.put(this.U.array(), 0, this.U.position());
            this.U = allocate;
        }
        return 0;
    }

    public byte[] buildFinish() {
        if (this.V) {
            b(1);
            this.U.put((byte) 125);
            Object obj = new byte[this.U.position()];
            System.arraycopy(this.U.array(), 0, obj, 0, obj.length);
            return obj;
        }
        throw new Exception("Buffer For Parse");
    }

    public boolean checkGetFinish() {
        return this.U.limit() - this.U.position() <= 1;
    }

    public int getInt() {
        if (!this.V) {
            return this.U.getInt();
        }
        throw new Exception("Buffer For Build");
    }

    public long getLong() {
        if (!this.V) {
            return this.U.getLong();
        }
        throw new Exception("Buffer For Build");
    }

    public String getString() {
        if (this.V) {
            throw new Exception("Buffer For Build");
        }
        short s = this.U.getShort();
        if (s > (short) 2048) {
            this.U = null;
            throw new Exception("Buffer String Length Error");
        } else if (s == (short) 0) {
            return "";
        } else {
            byte[] bArr = new byte[s];
            this.U.get(bArr, 0, s);
            return new String(bArr);
        }
    }

    public int initBuild() {
        this.U = ByteBuffer.allocate(4096);
        this.U.put((byte) 123);
        this.V = true;
        return 0;
    }

    public int initParse(byte[] bArr) {
        boolean z = (bArr == null || bArr.length == 0) ? true : bArr[0] != (byte) 123 ? true : bArr[bArr.length + -1] != (byte) 125 ? true : false;
        if (z) {
            this.U = null;
            return -1;
        }
        this.U = ByteBuffer.wrap(bArr);
        this.U.position(1);
        this.V = false;
        return 0;
    }

    public int putInt(int i) {
        if (this.V) {
            b(4);
            this.U.putInt(i);
            return 0;
        }
        throw new Exception("Buffer For Parse");
    }

    public int putLong(long j) {
        if (this.V) {
            b(8);
            this.U.putLong(j);
            return 0;
        }
        throw new Exception("Buffer For Parse");
    }

    public int putString(String str) {
        if (this.V) {
            byte[] bArr = null;
            if (str != null) {
                bArr = str.getBytes();
            }
            if (bArr == null) {
                bArr = new byte[0];
            }
            if (bArr.length > 2048) {
                throw new Exception("Buffer String Length Error");
            }
            b(bArr.length + 2);
            this.U.putShort((short) bArr.length);
            if (bArr.length > 0) {
                this.U.put(bArr);
            }
            return 0;
        }
        throw new Exception("Buffer For Parse");
    }
}
