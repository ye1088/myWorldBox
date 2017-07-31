package com.integralblue.httpresponsecache.compat.libcore.net.http;

import com.integralblue.httpresponsecache.compat.java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.tools.tar.TarConstants;

final class ChunkedOutputStream extends AbstractHttpOutputStream {
    private static final byte[] CRLF = new byte[]{(byte) 13, (byte) 10};
    private static final byte[] FINAL_CHUNK = new byte[]{TarConstants.LF_NORMAL, (byte) 13, (byte) 10, (byte) 13, (byte) 10};
    private static final byte[] HEX_DIGITS = new byte[]{TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, (byte) 56, (byte) 57, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
    private final ByteArrayOutputStream bufferedChunk;
    private final byte[] hex = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 13, (byte) 10};
    private final int maxChunkLength;
    private final OutputStream socketOut;

    public ChunkedOutputStream(OutputStream socketOut, int maxChunkLength) {
        this.socketOut = socketOut;
        this.maxChunkLength = Math.max(1, dataLength(maxChunkLength));
        this.bufferedChunk = new ByteArrayOutputStream(maxChunkLength);
    }

    private int dataLength(int dataPlusHeaderLength) {
        int headerLength = 4;
        for (int i = dataPlusHeaderLength - 4; i > 0; i >>= 4) {
            headerLength++;
        }
        return dataPlusHeaderLength - headerLength;
    }

    public synchronized void write(byte[] buffer, int offset, int count) throws IOException {
        checkNotClosed();
        Arrays.checkOffsetAndCount(buffer.length, offset, count);
        while (count > 0) {
            int numBytesWritten;
            if (this.bufferedChunk.size() > 0 || count < this.maxChunkLength) {
                numBytesWritten = Math.min(count, this.maxChunkLength - this.bufferedChunk.size());
                this.bufferedChunk.write(buffer, offset, numBytesWritten);
                if (this.bufferedChunk.size() == this.maxChunkLength) {
                    writeBufferedChunkToSocket();
                }
            } else {
                numBytesWritten = this.maxChunkLength;
                writeHex(numBytesWritten);
                this.socketOut.write(buffer, offset, numBytesWritten);
                this.socketOut.write(CRLF);
            }
            offset += numBytesWritten;
            count -= numBytesWritten;
        }
    }

    private void writeHex(int i) throws IOException {
        int cursor = 8;
        do {
            cursor--;
            this.hex[cursor] = HEX_DIGITS[i & 15];
            i >>>= 4;
        } while (i != 0);
        this.socketOut.write(this.hex, cursor, this.hex.length - cursor);
    }

    public synchronized void flush() throws IOException {
        if (!this.closed) {
            writeBufferedChunkToSocket();
            this.socketOut.flush();
        }
    }

    public synchronized void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            writeBufferedChunkToSocket();
            this.socketOut.write(FINAL_CHUNK);
        }
    }

    private void writeBufferedChunkToSocket() throws IOException {
        int size = this.bufferedChunk.size();
        if (size > 0) {
            writeHex(size);
            this.bufferedChunk.writeTo(this.socketOut);
            this.bufferedChunk.reset();
            this.socketOut.write(CRLF);
        }
    }
}
