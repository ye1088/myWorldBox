package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.libcore.io.Streams;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;

abstract class AbstractHttpInputStream extends InputStream {
    private final OutputStream cacheBody;
    private final CacheRequest cacheRequest;
    protected boolean closed;
    protected final HttpEngine httpEngine;
    protected final InputStream in;

    AbstractHttpInputStream(InputStream in, HttpEngine httpEngine, CacheRequest cacheRequest) throws IOException {
        this.in = in;
        this.httpEngine = httpEngine;
        OutputStream cacheBody = cacheRequest != null ? cacheRequest.getBody() : null;
        if (cacheBody == null) {
            cacheRequest = null;
        }
        this.cacheBody = cacheBody;
        this.cacheRequest = cacheRequest;
    }

    public final int read() throws IOException {
        return Streams.readSingleByte(this);
    }

    protected final void checkNotClosed() throws IOException {
        if (this.closed) {
            throw new IOException("stream closed");
        }
    }

    protected final void cacheWrite(byte[] buffer, int offset, int count) throws IOException {
        if (this.cacheBody != null) {
            this.cacheBody.write(buffer, offset, count);
        }
    }

    protected final void endOfInput(boolean reuseSocket) throws IOException {
        if (this.cacheRequest != null) {
            this.cacheBody.close();
        }
        this.httpEngine.release(reuseSocket);
    }

    protected final void unexpectedEndOfInput() {
        if (this.cacheRequest != null) {
            this.cacheRequest.abort();
        }
        this.httpEngine.release(false);
    }
}
