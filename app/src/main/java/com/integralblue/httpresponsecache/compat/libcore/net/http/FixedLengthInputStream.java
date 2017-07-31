package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheRequest;

final class FixedLengthInputStream extends AbstractHttpInputStream {
    private int bytesRemaining;

    public FixedLengthInputStream(InputStream is, CacheRequest cacheRequest, HttpEngine httpEngine, int length) throws IOException {
        super(is, httpEngine, cacheRequest);
        this.bytesRemaining = length;
        if (this.bytesRemaining == 0) {
            endOfInput(true);
        }
    }

    public int read(byte[] buffer, int offset, int count) throws IOException {
        Arrays.checkOffsetAndCount(buffer.length, offset, count);
        checkNotClosed();
        if (this.bytesRemaining == 0) {
            return -1;
        }
        int read = this.in.read(buffer, offset, Math.min(count, this.bytesRemaining));
        if (read == -1) {
            unexpectedEndOfInput();
            throw new IOException("unexpected end of stream");
        }
        this.bytesRemaining -= read;
        cacheWrite(buffer, offset, read);
        if (this.bytesRemaining != 0) {
            return read;
        }
        endOfInput(true);
        return read;
    }

    public int available() throws IOException {
        checkNotClosed();
        return this.bytesRemaining == 0 ? 0 : Math.min(this.in.available(), this.bytesRemaining);
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (this.bytesRemaining != 0) {
                unexpectedEndOfInput();
            }
        }
    }
}
