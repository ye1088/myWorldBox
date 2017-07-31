package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

final class RetryableOutputStream extends AbstractHttpOutputStream {
    private final ByteArrayOutputStream content;
    private final int limit;

    public RetryableOutputStream(int limit) {
        this.limit = limit;
        this.content = new ByteArrayOutputStream(limit);
    }

    public RetryableOutputStream() {
        this.limit = -1;
        this.content = new ByteArrayOutputStream();
    }

    public synchronized void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (this.content.size() < this.limit) {
                throw new IOException("content-length promised " + this.limit + " bytes, but received " + this.content.size());
            }
        }
    }

    public synchronized void write(byte[] buffer, int offset, int count) throws IOException {
        checkNotClosed();
        Arrays.checkOffsetAndCount(buffer.length, offset, count);
        if (this.limit == -1 || this.content.size() <= this.limit - count) {
            this.content.write(buffer, offset, count);
        } else {
            throw new IOException("exceeded content-length limit of " + this.limit + " bytes");
        }
    }

    public synchronized int contentLength() throws IOException {
        close();
        return this.content.size();
    }

    public void writeToSocket(OutputStream socketOut) throws IOException {
        this.content.writeTo(socketOut);
    }
}
