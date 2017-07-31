package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ChunkedNioStream implements ChunkedInput<ByteBuf> {
    private final ByteBuffer byteBuffer;
    private final int chunkSize;
    private final ReadableByteChannel in;
    private long offset;

    public io.netty.buffer.ByteBuf readChunk(io.netty.buffer.ByteBufAllocator r9) throws java.lang.Exception {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r4 = r8.isEndOfInput();
        if (r4 == 0) goto L_0x0008;
    L_0x0006:
        r0 = 0;
    L_0x0007:
        return r0;
    L_0x0008:
        r4 = r8.byteBuffer;
        r2 = r4.position();
    L_0x000e:
        r4 = r8.in;
        r5 = r8.byteBuffer;
        r1 = r4.read(r5);
        if (r1 >= 0) goto L_0x0039;
    L_0x0018:
        r4 = r8.byteBuffer;
        r4.flip();
        r3 = 1;
        r4 = r8.byteBuffer;
        r4 = r4.remaining();
        r0 = r9.buffer(r4);
        r4 = r8.byteBuffer;	 Catch:{ all -> 0x0045 }
        r0.writeBytes(r4);	 Catch:{ all -> 0x0045 }
        r4 = r8.byteBuffer;	 Catch:{ all -> 0x0045 }
        r4.clear();	 Catch:{ all -> 0x0045 }
        r3 = 0;
        if (r3 == 0) goto L_0x0007;
    L_0x0035:
        r0.release();
        goto L_0x0007;
    L_0x0039:
        r2 = r2 + r1;
        r4 = r8.offset;
        r6 = (long) r1;
        r4 = r4 + r6;
        r8.offset = r4;
        r4 = r8.chunkSize;
        if (r2 != r4) goto L_0x000e;
    L_0x0044:
        goto L_0x0018;
    L_0x0045:
        r4 = move-exception;
        if (r3 == 0) goto L_0x004b;
    L_0x0048:
        r0.release();
    L_0x004b:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.stream.ChunkedNioStream.readChunk(io.netty.buffer.ByteBufAllocator):io.netty.buffer.ByteBuf");
    }

    public ChunkedNioStream(ReadableByteChannel in) {
        this(in, 8192);
    }

    public ChunkedNioStream(ReadableByteChannel in, int chunkSize) {
        if (in == null) {
            throw new NullPointerException("in");
        } else if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize: " + chunkSize + " (expected: a positive integer)");
        } else {
            this.in = in;
            this.offset = 0;
            this.chunkSize = chunkSize;
            this.byteBuffer = ByteBuffer.allocate(chunkSize);
        }
    }

    public long transferredBytes() {
        return this.offset;
    }

    public boolean isEndOfInput() throws Exception {
        if (this.byteBuffer.position() > 0) {
            return false;
        }
        if (!this.in.isOpen()) {
            return true;
        }
        int b = this.in.read(this.byteBuffer);
        if (b < 0) {
            return true;
        }
        this.offset += (long) b;
        return false;
    }

    public void close() throws Exception {
        this.in.close();
    }

    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
        return readChunk(ctx.alloc());
    }

    public long length() {
        return -1;
    }

    public long progress() {
        return this.offset;
    }
}
