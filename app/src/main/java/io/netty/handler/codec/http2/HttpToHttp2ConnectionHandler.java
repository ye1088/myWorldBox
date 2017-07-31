package io.netty.handler.codec.http2;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http2.HttpConversionUtil.ExtensionHeaderNames;

public class HttpToHttp2ConnectionHandler extends Http2ConnectionHandler {
    private int currentStreamId;
    private final boolean validateHeaders;

    public void write(io.netty.channel.ChannelHandlerContext r26, java.lang.Object r27, io.netty.channel.ChannelPromise r28) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00ed in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r25 = this;
        r0 = r27;
        r3 = r0 instanceof io.netty.handler.codec.http.HttpMessage;
        if (r3 != 0) goto L_0x0010;
    L_0x0006:
        r0 = r27;
        r3 = r0 instanceof io.netty.handler.codec.http.HttpContent;
        if (r3 != 0) goto L_0x0010;
    L_0x000c:
        r26.write(r27, r28);
    L_0x000f:
        return;
    L_0x0010:
        r23 = 1;
        r22 = new io.netty.handler.codec.http2.Http2CodecUtil$SimpleChannelPromiseAggregator;
        r3 = r26.channel();
        r4 = r26.executor();
        r0 = r22;
        r1 = r28;
        r0.<init>(r1, r3, r4);
        r2 = r25.encoder();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r7 = 0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0 instanceof io.netty.handler.codec.http.HttpMessage;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r3 == 0) goto L_0x0070;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x002e:
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = (io.netty.handler.codec.http.HttpMessage) r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r19 = r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r19.headers();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r25;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0.getStreamId(r3);	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r25;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0.currentStreamId = r3;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r25;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0.validateHeaders;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r19;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r5 = io.netty.handler.codec.http2.HttpConversionUtil.toHttp2Headers(r0, r3);	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0 instanceof io.netty.handler.codec.http.FullHttpMessage;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r3 == 0) goto L_0x00dc;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0052:
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = (io.netty.handler.codec.http.FullHttpMessage) r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r3.content();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r3.isReadable();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r3 != 0) goto L_0x00dc;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0061:
        r7 = 1;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0062:
        r0 = r25;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r4 = r0.currentStreamId;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r6 = 0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r8 = r22.newPromise();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r26;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r2.writeHeaders(r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0070:
        if (r7 != 0) goto L_0x00d2;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0072:
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0 instanceof io.netty.handler.codec.http.HttpContent;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r3 == 0) goto L_0x00d2;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0078:
        r20 = 0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r15 = io.netty.handler.codec.http2.EmptyHttp2Headers.INSTANCE;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0 instanceof io.netty.handler.codec.http.LastHttpContent;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r3 == 0) goto L_0x0096;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0082:
        r20 = 1;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = (io.netty.handler.codec.http.LastHttpContent) r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r21 = r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r21.trailingHeaders();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r25;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r4 = r0.validateHeaders;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r15 = io.netty.handler.codec.http2.HttpConversionUtil.toHttp2Headers(r3, r4);	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x0096:
        r0 = r27;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = (io.netty.handler.codec.http.HttpContent) r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r11 = r3.content();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r20 == 0) goto L_0x00de;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x00a1:
        r3 = r15.isEmpty();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r3 == 0) goto L_0x00de;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x00a7:
        r7 = 1;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x00a8:
        r23 = 0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0 = r25;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r10 = r0.currentStreamId;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r12 = 0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r14 = r22.newPromise();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r8 = r2;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r9 = r26;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r13 = r7;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r8.writeData(r9, r10, r11, r12, r13, r14);	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r3 = r15.isEmpty();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r3 != 0) goto L_0x00d2;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x00c0:
        r0 = r25;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r14 = r0.currentStreamId;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r16 = 0;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r17 = 1;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r18 = r22.newPromise();	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r12 = r2;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r13 = r26;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r12.writeHeaders(r13, r14, r15, r16, r17, r18);	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
    L_0x00d2:
        if (r23 == 0) goto L_0x00d7;
    L_0x00d4:
        io.netty.util.ReferenceCountUtil.release(r27);
    L_0x00d7:
        r22.doneAllocatingPromises();
        goto L_0x000f;
    L_0x00dc:
        r7 = 0;
        goto L_0x0062;
    L_0x00de:
        r7 = 0;
        goto L_0x00a8;
    L_0x00e0:
        r24 = move-exception;
        r0 = r22;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r1 = r24;	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        r0.setFailure(r1);	 Catch:{ Throwable -> 0x00e0, all -> 0x00f2 }
        if (r23 == 0) goto L_0x00ed;
    L_0x00ea:
        io.netty.util.ReferenceCountUtil.release(r27);
    L_0x00ed:
        r22.doneAllocatingPromises();
        goto L_0x000f;
    L_0x00f2:
        r3 = move-exception;
        if (r23 == 0) goto L_0x00f8;
    L_0x00f5:
        io.netty.util.ReferenceCountUtil.release(r27);
    L_0x00f8:
        r22.doneAllocatingPromises();
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.HttpToHttp2ConnectionHandler.write(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.channel.ChannelPromise):void");
    }

    protected HttpToHttp2ConnectionHandler(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings, boolean validateHeaders) {
        super(decoder, encoder, initialSettings);
        this.validateHeaders = validateHeaders;
    }

    private int getStreamId(HttpHeaders httpHeaders) throws Exception {
        return httpHeaders.getInt(ExtensionHeaderNames.STREAM_ID.text(), connection().local().incrementAndGetNextStreamId());
    }
}
