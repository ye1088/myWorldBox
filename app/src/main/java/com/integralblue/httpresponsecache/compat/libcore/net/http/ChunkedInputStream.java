package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.java.util.Arrays;
import com.integralblue.httpresponsecache.compat.libcore.io.Streams;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheRequest;

final class ChunkedInputStream extends AbstractHttpInputStream {
    private static final int MIN_LAST_CHUNK_LENGTH = "\r\n0\r\n\r\n".length();
    private static final int NO_CHUNK_YET = -1;
    private int bytesRemainingInChunk = -1;
    private boolean hasMoreChunks = true;

    ChunkedInputStream(InputStream is, CacheRequest cacheRequest, HttpEngine httpEngine) throws IOException {
        super(is, httpEngine, cacheRequest);
    }

    public int read(byte[] buffer, int offset, int count) throws IOException {
        Arrays.checkOffsetAndCount(buffer.length, offset, count);
        checkNotClosed();
        if (!this.hasMoreChunks) {
            return -1;
        }
        if (this.bytesRemainingInChunk == 0 || this.bytesRemainingInChunk == -1) {
            readChunkSize();
            if (!this.hasMoreChunks) {
                return -1;
            }
        }
        int read = this.in.read(buffer, offset, Math.min(count, this.bytesRemainingInChunk));
        if (read == -1) {
            unexpectedEndOfInput();
            throw new IOException("unexpected end of stream");
        }
        this.bytesRemainingInChunk -= read;
        cacheWrite(buffer, offset, read);
        if (this.bytesRemainingInChunk != 0 || this.in.available() < MIN_LAST_CHUNK_LENGTH) {
            return read;
        }
        readChunkSize();
        return read;
    }

    private void readChunkSize() throws IOException {
        if (this.bytesRemainingInChunk != -1) {
            Streams.readAsciiLine(this.in);
        }
        String chunkSizeString = Streams.readAsciiLine(this.in);
        int index = chunkSizeString.indexOf(";");
        if (index != -1) {
            chunkSizeString = chunkSizeString.substring(0, index);
        }
        try {
            this.bytesRemainingInChunk = Integer.parseInt(chunkSizeString.trim(), 16);
            if (this.bytesRemainingInChunk == 0) {
                this.hasMoreChunks = false;
                this.httpEngine.readTrailers();
                endOfInput(true);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Expected a hex chunk size, but was " + chunkSizeString);
        }
    }

    public int available() throws IOException {
        checkNotClosed();
        if (!this.hasMoreChunks || this.bytesRemainingInChunk == -1) {
            return 0;
        }
        return Math.min(this.in.available(), this.bytesRemainingInChunk);
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (this.hasMoreChunks) {
                unexpectedEndOfInput();
            }
        }
    }
}
