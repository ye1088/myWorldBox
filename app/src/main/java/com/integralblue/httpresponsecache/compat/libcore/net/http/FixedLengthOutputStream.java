package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.java.util.Arrays;
import java.io.IOException;
import java.io.OutputStream;

final class FixedLengthOutputStream extends AbstractHttpOutputStream {
    private int bytesRemaining;
    private final OutputStream socketOut;

    public FixedLengthOutputStream(OutputStream socketOut, int bytesRemaining) {
        this.socketOut = socketOut;
        this.bytesRemaining = bytesRemaining;
    }

    public void write(byte[] buffer, int offset, int count) throws IOException {
        checkNotClosed();
        Arrays.checkOffsetAndCount(buffer.length, offset, count);
        if (count > this.bytesRemaining) {
            throw new IOException("expected " + this.bytesRemaining + " bytes but received " + count);
        }
        this.socketOut.write(buffer, offset, count);
        this.bytesRemaining -= count;
    }

    public void flush() throws IOException {
        if (!this.closed) {
            this.socketOut.flush();
        }
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (this.bytesRemaining > 0) {
                throw new IOException("unexpected end of stream");
            }
        }
    }
}
