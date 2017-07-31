package com.huluxia.image.core.common.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: LimitedInputStream */
public class a extends FilterInputStream {
    private int yC;
    private int yD;

    public a(InputStream inputStream, int limit) {
        super(inputStream);
        if (inputStream == null) {
            throw new NullPointerException();
        } else if (limit < 0) {
            throw new IllegalArgumentException("limit must be >= 0");
        } else {
            this.yC = limit;
            this.yD = -1;
        }
    }

    public int read() throws IOException {
        if (this.yC == 0) {
            return -1;
        }
        int readByte = this.in.read();
        if (readByte == -1) {
            return readByte;
        }
        this.yC--;
        return readByte;
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        if (this.yC == 0) {
            return -1;
        }
        int bytesRead = this.in.read(buffer, byteOffset, Math.min(byteCount, this.yC));
        if (bytesRead <= 0) {
            return bytesRead;
        }
        this.yC -= bytesRead;
        return bytesRead;
    }

    public long skip(long byteCount) throws IOException {
        long bytesSkipped = this.in.skip(Math.min(byteCount, (long) this.yC));
        this.yC = (int) (((long) this.yC) - bytesSkipped);
        return bytesSkipped;
    }

    public int available() throws IOException {
        return Math.min(this.in.available(), this.yC);
    }

    public void mark(int readLimit) {
        if (this.in.markSupported()) {
            this.in.mark(readLimit);
            this.yD = this.yC;
        }
    }

    public void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("mark is not supported");
        } else if (this.yD == -1) {
            throw new IOException("mark not set");
        } else {
            this.in.reset();
            this.yC = this.yD;
        }
    }
}
