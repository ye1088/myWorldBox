package com.integralblue.httpresponsecache.compat.libcore.net.http;

import java.io.IOException;
import java.io.OutputStream;

abstract class AbstractHttpOutputStream extends OutputStream {
    protected boolean closed;

    AbstractHttpOutputStream() {
    }

    public final void write(int data) throws IOException {
        write(new byte[]{(byte) data});
    }

    protected final void checkNotClosed() throws IOException {
        if (this.closed) {
            throw new IOException("stream closed");
        }
    }
}
