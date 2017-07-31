package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class WebSocket00FrameEncoder extends MessageToMessageEncoder<WebSocketFrame> implements WebSocketFrameEncoder {
    private static final ByteBuf _0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(0));
    private static final ByteBuf _0XFF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(-1));
    private static final ByteBuf _0XFF_0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(2, 2).writeByte(-1).writeByte(0));

    protected void encode(io.netty.channel.ChannelHandlerContext r11, io.netty.handler.codec.http.websocketx.WebSocketFrame r12, java.util.List<java.lang.Object> r13) throws java.lang.Exception {
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
        r10 = this;
        r8 = r12 instanceof io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
        if (r8 == 0) goto L_0x0022;
    L_0x0004:
        r5 = r12.content();
        r8 = _0X00;
        r8 = r8.duplicate();
        r13.add(r8);
        r8 = r5.retain();
        r13.add(r8);
        r8 = _0XFF;
        r8 = r8.duplicate();
        r13.add(r8);
    L_0x0021:
        return;
    L_0x0022:
        r8 = r12 instanceof io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
        if (r8 == 0) goto L_0x0030;
    L_0x0026:
        r8 = _0XFF_0X00;
        r8 = r8.duplicate();
        r13.add(r8);
        goto L_0x0021;
    L_0x0030:
        r5 = r12.content();
        r6 = r5.readableBytes();
        r8 = r11.alloc();
        r9 = 5;
        r4 = r8.buffer(r9);
        r7 = 1;
        r8 = -128; // 0xffffffffffffff80 float:NaN double:NaN;
        r4.writeByte(r8);	 Catch:{ all -> 0x0078 }
        r8 = r6 >>> 28;	 Catch:{ all -> 0x0078 }
        r0 = r8 & 127;	 Catch:{ all -> 0x0078 }
        r8 = r6 >>> 14;	 Catch:{ all -> 0x0078 }
        r1 = r8 & 127;	 Catch:{ all -> 0x0078 }
        r8 = r6 >>> 7;	 Catch:{ all -> 0x0078 }
        r2 = r8 & 127;	 Catch:{ all -> 0x0078 }
        r3 = r6 & 127;	 Catch:{ all -> 0x0078 }
        if (r0 != 0) goto L_0x008d;	 Catch:{ all -> 0x0078 }
    L_0x0057:
        if (r1 != 0) goto L_0x007f;	 Catch:{ all -> 0x0078 }
    L_0x0059:
        if (r2 != 0) goto L_0x006f;	 Catch:{ all -> 0x0078 }
    L_0x005b:
        r4.writeByte(r3);	 Catch:{ all -> 0x0078 }
    L_0x005e:
        r13.add(r4);	 Catch:{ all -> 0x0078 }
        r8 = r5.retain();	 Catch:{ all -> 0x0078 }
        r13.add(r8);	 Catch:{ all -> 0x0078 }
        r7 = 0;
        if (r7 == 0) goto L_0x0021;
    L_0x006b:
        r4.release();
        goto L_0x0021;
    L_0x006f:
        r8 = r2 | 128;
        r4.writeByte(r8);	 Catch:{ all -> 0x0078 }
        r4.writeByte(r3);	 Catch:{ all -> 0x0078 }
        goto L_0x005e;
    L_0x0078:
        r8 = move-exception;
        if (r7 == 0) goto L_0x007e;
    L_0x007b:
        r4.release();
    L_0x007e:
        throw r8;
    L_0x007f:
        r8 = r1 | 128;
        r4.writeByte(r8);	 Catch:{ all -> 0x0078 }
        r8 = r2 | 128;	 Catch:{ all -> 0x0078 }
        r4.writeByte(r8);	 Catch:{ all -> 0x0078 }
        r4.writeByte(r3);	 Catch:{ all -> 0x0078 }
        goto L_0x005e;	 Catch:{ all -> 0x0078 }
    L_0x008d:
        r8 = r0 | 128;	 Catch:{ all -> 0x0078 }
        r4.writeByte(r8);	 Catch:{ all -> 0x0078 }
        r8 = r1 | 128;	 Catch:{ all -> 0x0078 }
        r4.writeByte(r8);	 Catch:{ all -> 0x0078 }
        r8 = r2 | 128;	 Catch:{ all -> 0x0078 }
        r4.writeByte(r8);	 Catch:{ all -> 0x0078 }
        r4.writeByte(r3);	 Catch:{ all -> 0x0078 }
        goto L_0x005e;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder.encode(io.netty.channel.ChannelHandlerContext, io.netty.handler.codec.http.websocketx.WebSocketFrame, java.util.List):void");
    }
}
