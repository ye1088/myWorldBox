package com.huluxia.framework.base.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends FilterOutputStream {
    private long mCount = 0;

    public CountingOutputStream(OutputStream out) {
        super(out);
    }

    public long getCount() {
        return this.mCount;
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
        this.mCount += (long) len;
    }

    public void write(int b) throws IOException {
        this.out.write(b);
        this.mCount++;
    }

    public void close() throws IOException {
        this.out.close();
    }
}
