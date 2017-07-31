package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;

public class CompressorHttp2ConnectionEncoder extends DecoratingHttp2ConnectionEncoder {
    public static final int DEFAULT_COMPRESSION_LEVEL = 6;
    public static final int DEFAULT_MEM_LEVEL = 8;
    public static final int DEFAULT_WINDOW_BITS = 15;
    private final int compressionLevel;
    private final int memLevel;
    private final PropertyKey propertyKey;
    private final int windowBits;

    public io.netty.channel.ChannelFuture writeData(io.netty.channel.ChannelHandlerContext r14, int r15, io.netty.buffer.ByteBuf r16, int r17, boolean r18, io.netty.channel.ChannelPromise r19) {
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
        r13 = this;
        r1 = r13.connection();
        r12 = r1.stream(r15);
        if (r12 != 0) goto L_0x0012;
    L_0x000a:
        r9 = 0;
    L_0x000b:
        if (r9 != 0) goto L_0x001c;
    L_0x000d:
        r19 = super.writeData(r14, r15, r16, r17, r18, r19);
    L_0x0011:
        return r19;
    L_0x0012:
        r1 = r13.propertyKey;
        r1 = r12.getProperty(r1);
        r1 = (io.netty.channel.embedded.EmbeddedChannel) r1;
        r9 = r1;
        goto L_0x000b;
    L_0x001c:
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r2 = 0;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r1[r2] = r16;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r9.writeOutbound(r1);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r4 = nextReadableBuf(r9);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r4 != 0) goto L_0x0056;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x002b:
        if (r18 == 0) goto L_0x004d;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x002d:
        r1 = r9.finish();	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r1 == 0) goto L_0x0037;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0033:
        r4 = nextReadableBuf(r9);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0037:
        if (r4 != 0) goto L_0x003b;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0039:
        r4 = io.netty.buffer.Unpooled.EMPTY_BUFFER;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x003b:
        r6 = 1;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r1 = r13;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r2 = r14;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r3 = r15;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r5 = r17;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r7 = r19;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r19 = super.writeData(r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r18 == 0) goto L_0x0011;
    L_0x0049:
        r13.cleanup(r12, r9);
        goto L_0x0011;
    L_0x004d:
        r19.setSuccess();	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r18 == 0) goto L_0x0011;
    L_0x0052:
        r13.cleanup(r12, r9);
        goto L_0x0011;
    L_0x0056:
        r10 = new io.netty.util.concurrent.PromiseCombiner;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r10.<init>();	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x005b:
        r11 = nextReadableBuf(r9);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r11 != 0) goto L_0x008f;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0061:
        if (r18 == 0) goto L_0x008f;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0063:
        r6 = 1;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0064:
        if (r6 == 0) goto L_0x0073;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0066:
        r1 = r9.finish();	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r1 == 0) goto L_0x0073;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x006c:
        r11 = nextReadableBuf(r9);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r11 != 0) goto L_0x0091;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0072:
        r6 = 1;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0073:
        r7 = r14.newPromise();	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r10.add(r7);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r1 = r13;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r2 = r14;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r3 = r15;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r5 = r17;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        super.writeData(r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r11 != 0) goto L_0x0093;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
    L_0x0084:
        r0 = r19;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r10.finish(r0);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r18 == 0) goto L_0x0011;
    L_0x008b:
        r13.cleanup(r12, r9);
        goto L_0x0011;
    L_0x008f:
        r6 = 0;
        goto L_0x0064;
    L_0x0091:
        r6 = 0;
        goto L_0x0073;
    L_0x0093:
        r17 = 0;
        r4 = r11;
        goto L_0x005b;
    L_0x0097:
        r8 = move-exception;
        r0 = r19;	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        r0.tryFailure(r8);	 Catch:{ Throwable -> 0x0097, all -> 0x00a4 }
        if (r18 == 0) goto L_0x0011;
    L_0x009f:
        r13.cleanup(r12, r9);
        goto L_0x0011;
    L_0x00a4:
        r1 = move-exception;
        if (r18 == 0) goto L_0x00aa;
    L_0x00a7:
        r13.cleanup(r12, r9);
    L_0x00aa:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.CompressorHttp2ConnectionEncoder.writeData(io.netty.channel.ChannelHandlerContext, int, io.netty.buffer.ByteBuf, int, boolean, io.netty.channel.ChannelPromise):io.netty.channel.ChannelFuture");
    }

    public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder delegate) {
        this(delegate, 6, 15, 8);
    }

    public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder delegate, int compressionLevel, int windowBits, int memLevel) {
        super(delegate);
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        } else if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        } else {
            this.compressionLevel = compressionLevel;
            this.windowBits = windowBits;
            this.memLevel = memLevel;
            this.propertyKey = connection().newKey();
            connection().addListener(new Http2ConnectionAdapter() {
                public void onStreamRemoved(Http2Stream stream) {
                    EmbeddedChannel compressor = (EmbeddedChannel) stream.getProperty(CompressorHttp2ConnectionEncoder.this.propertyKey);
                    if (compressor != null) {
                        CompressorHttp2ConnectionEncoder.this.cleanup(stream, compressor);
                    }
                }
            });
        }
    }

    public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream, ChannelPromise promise) {
        try {
            EmbeddedChannel compressor = newCompressor(ctx, headers, endStream);
            ChannelFuture future = super.writeHeaders(ctx, streamId, headers, padding, endStream, promise);
            bindCompressorToStream(compressor, streamId);
            return future;
        } catch (Throwable e) {
            promise.tryFailure(e);
            return promise;
        }
    }

    public ChannelFuture writeHeaders(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream, ChannelPromise promise) {
        try {
            EmbeddedChannel compressor = newCompressor(ctx, headers, endOfStream);
            ChannelFuture future = super.writeHeaders(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream, promise);
            bindCompressorToStream(compressor, streamId);
            return future;
        } catch (Throwable e) {
            promise.tryFailure(e);
            return promise;
        }
    }

    protected EmbeddedChannel newContentCompressor(ChannelHandlerContext ctx, CharSequence contentEncoding) throws Http2Exception {
        if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(contentEncoding) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(contentEncoding)) {
            return newCompressionChannel(ctx, ZlibWrapper.GZIP);
        }
        if (HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(contentEncoding) || HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(contentEncoding)) {
            return newCompressionChannel(ctx, ZlibWrapper.ZLIB);
        }
        return null;
    }

    protected CharSequence getTargetContentEncoding(CharSequence contentEncoding) throws Http2Exception {
        return contentEncoding;
    }

    private EmbeddedChannel newCompressionChannel(ChannelHandlerContext ctx, ZlibWrapper wrapper) {
        return new EmbeddedChannel(ctx.channel().id(), ctx.channel().metadata().hasDisconnect(), ctx.channel().config(), new ChannelHandler[]{ZlibCodecFactory.newZlibEncoder(wrapper, this.compressionLevel, this.windowBits, this.memLevel)});
    }

    private EmbeddedChannel newCompressor(ChannelHandlerContext ctx, Http2Headers headers, boolean endOfStream) throws Http2Exception {
        if (endOfStream) {
            return null;
        }
        CharSequence encoding = (CharSequence) headers.get(HttpHeaderNames.CONTENT_ENCODING);
        if (encoding == null) {
            encoding = HttpHeaderValues.IDENTITY;
        }
        EmbeddedChannel compressor = newContentCompressor(ctx, encoding);
        if (compressor == null) {
            return compressor;
        }
        CharSequence targetContentEncoding = getTargetContentEncoding(encoding);
        if (HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(targetContentEncoding)) {
            headers.remove(HttpHeaderNames.CONTENT_ENCODING);
        } else {
            headers.set(HttpHeaderNames.CONTENT_ENCODING, targetContentEncoding);
        }
        headers.remove(HttpHeaderNames.CONTENT_LENGTH);
        return compressor;
    }

    private void bindCompressorToStream(EmbeddedChannel compressor, int streamId) {
        if (compressor != null) {
            Http2Stream stream = connection().stream(streamId);
            if (stream != null) {
                stream.setProperty(this.propertyKey, compressor);
            }
        }
    }

    void cleanup(Http2Stream stream, EmbeddedChannel compressor) {
        if (compressor.finish()) {
            while (true) {
                ByteBuf buf = (ByteBuf) compressor.readOutbound();
                if (buf == null) {
                    break;
                }
                buf.release();
            }
        }
        stream.removeProperty(this.propertyKey);
    }

    private static ByteBuf nextReadableBuf(EmbeddedChannel compressor) {
        while (true) {
            ByteBuf buf = (ByteBuf) compressor.readOutbound();
            if (buf == null) {
                return null;
            }
            if (buf.isReadable()) {
                return buf;
            }
            buf.release();
        }
    }
}
