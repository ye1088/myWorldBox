package com.huluxia.image.core.common.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: TailAppendingInputStream */
public class b extends FilterInputStream {
    private final byte[] yE;
    private int yF;
    private int yG;

    public b(InputStream inputStream, byte[] tail) {
        super(inputStream);
        if (inputStream == null) {
            throw new NullPointerException();
        } else if (tail == null) {
            throw new NullPointerException();
        } else {
            this.yE = tail;
        }
    }

    public int read() throws IOException {
        int readResult = this.in.read();
        return readResult != -1 ? readResult : iJ();
    }

    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int offset, int count) throws IOException {
        int readResult = this.in.read(buffer, offset, count);
        if (readResult != -1) {
            return readResult;
        }
        if (count == 0) {
            return 0;
        }
        int bytesRead = 0;
        while (bytesRead < count) {
            int nextByte = iJ();
            if (nextByte == -1) {
                break;
            }
            buffer[offset + bytesRead] = (byte) nextByte;
            bytesRead++;
        }
        if (bytesRead <= 0) {
            bytesRead = -1;
        }
        return bytesRead;
    }

    public void reset() throws IOException {
        if (this.in.markSupported()) {
            this.in.reset();
            this.yF = this.yG;
            return;
        }
        throw new IOException("mark is not supported");
    }

    public void mark(int readLimit) {
        if (this.in.markSupported()) {
            super.mark(readLimit);
            this.yG = this.yF;
        }
    }

    private int iJ() {
        if (this.yF >= this.yE.length) {
            return -1;
        }
        byte[] bArr = this.yE;
        int i = this.yF;
        this.yF = i + 1;
        return bArr[i] & 255;
    }
}
