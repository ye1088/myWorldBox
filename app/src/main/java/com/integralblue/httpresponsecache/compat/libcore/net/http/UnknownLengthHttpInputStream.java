package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheRequest;

final class UnknownLengthHttpInputStream extends AbstractHttpInputStream {
    private boolean inputExhausted;

    UnknownLengthHttpInputStream(InputStream is, CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
        super(is, httpEngine, cacheRequest);
    }

    public int read(byte[] buffer, int offset, int count) throws IOException {
        Arrays.checkOffsetAndCount(buffer.length, offset, count);
        checkNotClosed();
        if (this.in == null || this.inputExhausted) {
            return -1;
        }
        int read = this.in.read(buffer, offset, count);
        if (read == -1) {
            this.inputExhausted = true;
            endOfInput(false);
            return -1;
        }
        cacheWrite(buffer, offset, read);
        return read;
    }

    public int available() throws IOException {
        checkNotClosed();
        return this.in == null ? 0 : this.in.available();
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (!this.inputExhausted) {
                unexpectedEndOfInput();
            }
        }
    }
}
