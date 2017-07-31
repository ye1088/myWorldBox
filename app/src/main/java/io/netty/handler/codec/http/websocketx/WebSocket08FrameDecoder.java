package io.netty.handler.codec.http.websocketx;

import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteOrder;

public class WebSocket08FrameDecoder extends ByteToMessageDecoder implements WebSocketFrameDecoder {
    private static final byte OPCODE_BINARY = (byte) 2;
    private static final byte OPCODE_CLOSE = (byte) 8;
    private static final byte OPCODE_CONT = (byte) 0;
    private static final byte OPCODE_PING = (byte) 9;
    private static final byte OPCODE_PONG = (byte) 10;
    private static final byte OPCODE_TEXT = (byte) 1;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
    private final boolean allowExtensions;
    private final boolean allowMaskMismatch;
    private final boolean expectMaskedFrames;
    private int fragmentedFramesCount;
    private boolean frameFinalFlag;
    private boolean frameMasked;
    private int frameOpcode;
    private int framePayloadLen1;
    private long framePayloadLength;
    private int frameRsv;
    private byte[] maskingKey;
    private final long maxFramePayloadLength;
    private boolean receivedClosingHandshake;
    private State state;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State = new int[State.values().length];

        static {
            try {
                $SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State[State.READING_FIRST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State[State.READING_SECOND.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State[State.READING_SIZE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State[State.MASKING_KEY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State[State.PAYLOAD.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State[State.CORRUPT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    enum State {
        READING_FIRST,
        READING_SECOND,
        READING_SIZE,
        MASKING_KEY,
        PAYLOAD,
        CORRUPT
    }

    protected void decode(io.netty.channel.ChannelHandlerContext r12, io.netty.buffer.ByteBuf r13, java.util.List<java.lang.Object> r14) throws java.lang.Exception {
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
        r11 = this;
        r10 = 2;
        r4 = 0;
        r9 = 9;
        r8 = 8;
        r3 = 1;
        r2 = r11.receivedClosingHandshake;
        if (r2 == 0) goto L_0x0013;
    L_0x000b:
        r2 = r11.actualReadableBytes();
        r13.skipBytes(r2);
    L_0x0012:
        return;
    L_0x0013:
        r2 = io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State;
        r5 = r11.state;
        r5 = r5.ordinal();
        r2 = r2[r5];
        switch(r2) {
            case 1: goto L_0x0029;
            case 2: goto L_0x0062;
            case 3: goto L_0x0155;
            case 4: goto L_0x01e4;
            case 5: goto L_0x0201;
            case 6: goto L_0x02f3;
            default: goto L_0x0020;
        };
    L_0x0020:
        r2 = new java.lang.Error;
        r3 = "Shouldn't reach here.";
        r2.<init>(r3);
        throw r2;
    L_0x0029:
        r2 = r13.isReadable();
        if (r2 == 0) goto L_0x0012;
    L_0x002f:
        r6 = 0;
        r11.framePayloadLength = r6;
        r0 = r13.readByte();
        r2 = r0 & 128;
        if (r2 == 0) goto L_0x009a;
    L_0x003b:
        r2 = r3;
    L_0x003c:
        r11.frameFinalFlag = r2;
        r2 = r0 & 112;
        r2 = r2 >> 4;
        r11.frameRsv = r2;
        r2 = r0 & 15;
        r11.frameOpcode = r2;
        r2 = logger;
        r2 = r2.isDebugEnabled();
        if (r2 == 0) goto L_0x005e;
    L_0x0050:
        r2 = logger;
        r5 = "Decoding WebSocket Frame opCode={}";
        r6 = r11.frameOpcode;
        r6 = java.lang.Integer.valueOf(r6);
        r2.debug(r5, r6);
    L_0x005e:
        r2 = io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.READING_SECOND;
        r11.state = r2;
    L_0x0062:
        r2 = r13.isReadable();
        if (r2 == 0) goto L_0x0012;
    L_0x0068:
        r0 = r13.readByte();
        r2 = r0 & 128;
        if (r2 == 0) goto L_0x0071;
    L_0x0070:
        r4 = r3;
    L_0x0071:
        r11.frameMasked = r4;
        r2 = r0 & 127;
        r11.framePayloadLen1 = r2;
        r2 = r11.frameRsv;
        if (r2 == 0) goto L_0x009c;
    L_0x007b:
        r2 = r11.allowExtensions;
        if (r2 != 0) goto L_0x009c;
    L_0x007f:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "RSV != 0 and no extension negotiated, RSV:";
        r2 = r2.append(r3);
        r3 = r11.frameRsv;
        r2 = r2.append(r3);
        r2 = r2.toString();
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x009a:
        r2 = r4;
        goto L_0x003c;
    L_0x009c:
        r2 = r11.allowMaskMismatch;
        if (r2 != 0) goto L_0x00ae;
    L_0x00a0:
        r2 = r11.expectMaskedFrames;
        r4 = r11.frameMasked;
        if (r2 == r4) goto L_0x00ae;
    L_0x00a6:
        r2 = "received a frame that is not masked as expected";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x00ae:
        r2 = r11.frameOpcode;
        r4 = 7;
        if (r2 <= r4) goto L_0x0106;
    L_0x00b3:
        r2 = r11.frameFinalFlag;
        if (r2 != 0) goto L_0x00bf;
    L_0x00b7:
        r2 = "fragmented control frame";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x00bf:
        r2 = r11.framePayloadLen1;
        r4 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        if (r2 <= r4) goto L_0x00cd;
    L_0x00c5:
        r2 = "control frame with payload length > 125 octets";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x00cd:
        r2 = r11.frameOpcode;
        if (r2 == r8) goto L_0x00f6;
    L_0x00d1:
        r2 = r11.frameOpcode;
        if (r2 == r9) goto L_0x00f6;
    L_0x00d5:
        r2 = r11.frameOpcode;
        r4 = 10;
        if (r2 == r4) goto L_0x00f6;
    L_0x00db:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "control frame using reserved opcode ";
        r2 = r2.append(r3);
        r3 = r11.frameOpcode;
        r2 = r2.append(r3);
        r2 = r2.toString();
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x00f6:
        r2 = r11.frameOpcode;
        if (r2 != r8) goto L_0x0151;
    L_0x00fa:
        r2 = r11.framePayloadLen1;
        if (r2 != r3) goto L_0x0151;
    L_0x00fe:
        r2 = "received close control frame with payload len 1";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x0106:
        r2 = r11.frameOpcode;
        if (r2 == 0) goto L_0x012d;
    L_0x010a:
        r2 = r11.frameOpcode;
        if (r2 == r3) goto L_0x012d;
    L_0x010e:
        r2 = r11.frameOpcode;
        if (r2 == r10) goto L_0x012d;
    L_0x0112:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "data frame using reserved opcode ";
        r2 = r2.append(r3);
        r3 = r11.frameOpcode;
        r2 = r2.append(r3);
        r2 = r2.toString();
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x012d:
        r2 = r11.fragmentedFramesCount;
        if (r2 != 0) goto L_0x013d;
    L_0x0131:
        r2 = r11.frameOpcode;
        if (r2 != 0) goto L_0x013d;
    L_0x0135:
        r2 = "received continuation data frame outside fragmented message";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x013d:
        r2 = r11.fragmentedFramesCount;
        if (r2 == 0) goto L_0x0151;
    L_0x0141:
        r2 = r11.frameOpcode;
        if (r2 == 0) goto L_0x0151;
    L_0x0145:
        r2 = r11.frameOpcode;
        if (r2 == r9) goto L_0x0151;
    L_0x0149:
        r2 = "received non-continuation data frame while inside fragmented message";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x0151:
        r2 = io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.READING_SIZE;
        r11.state = r2;
    L_0x0155:
        r2 = r11.framePayloadLen1;
        r4 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        if (r2 != r4) goto L_0x0178;
    L_0x015b:
        r2 = r13.readableBytes();
        if (r2 < r10) goto L_0x0012;
    L_0x0161:
        r2 = r13.readUnsignedShort();
        r4 = (long) r2;
        r11.framePayloadLength = r4;
        r4 = r11.framePayloadLength;
        r6 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 >= 0) goto L_0x01a0;
    L_0x0170:
        r2 = "invalid data frame length (not using minimal length encoding)";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x0178:
        r2 = r11.framePayloadLen1;
        r4 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        if (r2 != r4) goto L_0x019b;
    L_0x017e:
        r2 = r13.readableBytes();
        if (r2 < r8) goto L_0x0012;
    L_0x0184:
        r4 = r13.readLong();
        r11.framePayloadLength = r4;
        r4 = r11.framePayloadLength;
        r6 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 >= 0) goto L_0x01a0;
    L_0x0193:
        r2 = "invalid data frame length (not using minimal length encoding)";
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x019b:
        r2 = r11.framePayloadLen1;
        r4 = (long) r2;
        r11.framePayloadLength = r4;
    L_0x01a0:
        r4 = r11.framePayloadLength;
        r6 = r11.maxFramePayloadLength;
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 <= 0) goto L_0x01ca;
    L_0x01a8:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Max frame length of ";
        r2 = r2.append(r3);
        r4 = r11.maxFramePayloadLength;
        r2 = r2.append(r4);
        r3 = " has been exceeded.";
        r2 = r2.append(r3);
        r2 = r2.toString();
        r11.protocolViolation(r12, r2);
        goto L_0x0012;
    L_0x01ca:
        r2 = logger;
        r2 = r2.isDebugEnabled();
        if (r2 == 0) goto L_0x01e0;
    L_0x01d2:
        r2 = logger;
        r4 = "Decoding WebSocket Frame length={}";
        r6 = r11.framePayloadLength;
        r5 = java.lang.Long.valueOf(r6);
        r2.debug(r4, r5);
    L_0x01e0:
        r2 = io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.MASKING_KEY;
        r11.state = r2;
    L_0x01e4:
        r2 = r11.frameMasked;
        if (r2 == 0) goto L_0x01fd;
    L_0x01e8:
        r2 = r13.readableBytes();
        r4 = 4;
        if (r2 < r4) goto L_0x0012;
    L_0x01ef:
        r2 = r11.maskingKey;
        if (r2 != 0) goto L_0x01f8;
    L_0x01f3:
        r2 = 4;
        r2 = new byte[r2];
        r11.maskingKey = r2;
    L_0x01f8:
        r2 = r11.maskingKey;
        r13.readBytes(r2);
    L_0x01fd:
        r2 = io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.PAYLOAD;
        r11.state = r2;
    L_0x0201:
        r2 = r13.readableBytes();
        r4 = (long) r2;
        r6 = r11.framePayloadLength;
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 < 0) goto L_0x0012;
    L_0x020c:
        r1 = 0;
        r2 = r12.alloc();	 Catch:{ all -> 0x02a0 }
        r4 = r11.framePayloadLength;	 Catch:{ all -> 0x02a0 }
        r4 = toFrameLength(r4);	 Catch:{ all -> 0x02a0 }
        r1 = io.netty.buffer.ByteBufUtil.readBytes(r2, r13, r4);	 Catch:{ all -> 0x02a0 }
        r2 = io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.READING_FIRST;	 Catch:{ all -> 0x02a0 }
        r11.state = r2;	 Catch:{ all -> 0x02a0 }
        r2 = r11.frameMasked;	 Catch:{ all -> 0x02a0 }
        if (r2 == 0) goto L_0x0226;	 Catch:{ all -> 0x02a0 }
    L_0x0223:
        r11.unmask(r1);	 Catch:{ all -> 0x02a0 }
    L_0x0226:
        r2 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        if (r2 != r9) goto L_0x023e;	 Catch:{ all -> 0x02a0 }
    L_0x022a:
        r2 = new io.netty.handler.codec.http.websocketx.PingWebSocketFrame;	 Catch:{ all -> 0x02a0 }
        r3 = r11.frameFinalFlag;	 Catch:{ all -> 0x02a0 }
        r4 = r11.frameRsv;	 Catch:{ all -> 0x02a0 }
        r2.<init>(r3, r4, r1);	 Catch:{ all -> 0x02a0 }
        r14.add(r2);	 Catch:{ all -> 0x02a0 }
        r1 = 0;
        if (r1 == 0) goto L_0x0012;
    L_0x0239:
        r1.release();
        goto L_0x0012;
    L_0x023e:
        r2 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        r4 = 10;	 Catch:{ all -> 0x02a0 }
        if (r2 != r4) goto L_0x0258;	 Catch:{ all -> 0x02a0 }
    L_0x0244:
        r2 = new io.netty.handler.codec.http.websocketx.PongWebSocketFrame;	 Catch:{ all -> 0x02a0 }
        r3 = r11.frameFinalFlag;	 Catch:{ all -> 0x02a0 }
        r4 = r11.frameRsv;	 Catch:{ all -> 0x02a0 }
        r2.<init>(r3, r4, r1);	 Catch:{ all -> 0x02a0 }
        r14.add(r2);	 Catch:{ all -> 0x02a0 }
        r1 = 0;
        if (r1 == 0) goto L_0x0012;
    L_0x0253:
        r1.release();
        goto L_0x0012;
    L_0x0258:
        r2 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        if (r2 != r8) goto L_0x0276;	 Catch:{ all -> 0x02a0 }
    L_0x025c:
        r2 = 1;	 Catch:{ all -> 0x02a0 }
        r11.receivedClosingHandshake = r2;	 Catch:{ all -> 0x02a0 }
        r11.checkCloseFrameBody(r12, r1);	 Catch:{ all -> 0x02a0 }
        r2 = new io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;	 Catch:{ all -> 0x02a0 }
        r3 = r11.frameFinalFlag;	 Catch:{ all -> 0x02a0 }
        r4 = r11.frameRsv;	 Catch:{ all -> 0x02a0 }
        r2.<init>(r3, r4, r1);	 Catch:{ all -> 0x02a0 }
        r14.add(r2);	 Catch:{ all -> 0x02a0 }
        r1 = 0;
        if (r1 == 0) goto L_0x0012;
    L_0x0271:
        r1.release();
        goto L_0x0012;
    L_0x0276:
        r2 = r11.frameFinalFlag;	 Catch:{ all -> 0x02a0 }
        if (r2 == 0) goto L_0x0299;	 Catch:{ all -> 0x02a0 }
    L_0x027a:
        r2 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        if (r2 == r9) goto L_0x0281;	 Catch:{ all -> 0x02a0 }
    L_0x027e:
        r2 = 0;	 Catch:{ all -> 0x02a0 }
        r11.fragmentedFramesCount = r2;	 Catch:{ all -> 0x02a0 }
    L_0x0281:
        r2 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        if (r2 != r3) goto L_0x02a7;	 Catch:{ all -> 0x02a0 }
    L_0x0285:
        r2 = new io.netty.handler.codec.http.websocketx.TextWebSocketFrame;	 Catch:{ all -> 0x02a0 }
        r3 = r11.frameFinalFlag;	 Catch:{ all -> 0x02a0 }
        r4 = r11.frameRsv;	 Catch:{ all -> 0x02a0 }
        r2.<init>(r3, r4, r1);	 Catch:{ all -> 0x02a0 }
        r14.add(r2);	 Catch:{ all -> 0x02a0 }
        r1 = 0;
        if (r1 == 0) goto L_0x0012;
    L_0x0294:
        r1.release();
        goto L_0x0012;
    L_0x0299:
        r2 = r11.fragmentedFramesCount;	 Catch:{ all -> 0x02a0 }
        r2 = r2 + 1;	 Catch:{ all -> 0x02a0 }
        r11.fragmentedFramesCount = r2;	 Catch:{ all -> 0x02a0 }
        goto L_0x0281;
    L_0x02a0:
        r2 = move-exception;
        if (r1 == 0) goto L_0x02a6;
    L_0x02a3:
        r1.release();
    L_0x02a6:
        throw r2;
    L_0x02a7:
        r2 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        if (r2 != r10) goto L_0x02bf;	 Catch:{ all -> 0x02a0 }
    L_0x02ab:
        r2 = new io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;	 Catch:{ all -> 0x02a0 }
        r3 = r11.frameFinalFlag;	 Catch:{ all -> 0x02a0 }
        r4 = r11.frameRsv;	 Catch:{ all -> 0x02a0 }
        r2.<init>(r3, r4, r1);	 Catch:{ all -> 0x02a0 }
        r14.add(r2);	 Catch:{ all -> 0x02a0 }
        r1 = 0;
        if (r1 == 0) goto L_0x0012;
    L_0x02ba:
        r1.release();
        goto L_0x0012;
    L_0x02bf:
        r2 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        if (r2 != 0) goto L_0x02d7;	 Catch:{ all -> 0x02a0 }
    L_0x02c3:
        r2 = new io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;	 Catch:{ all -> 0x02a0 }
        r3 = r11.frameFinalFlag;	 Catch:{ all -> 0x02a0 }
        r4 = r11.frameRsv;	 Catch:{ all -> 0x02a0 }
        r2.<init>(r3, r4, r1);	 Catch:{ all -> 0x02a0 }
        r14.add(r2);	 Catch:{ all -> 0x02a0 }
        r1 = 0;
        if (r1 == 0) goto L_0x0012;
    L_0x02d2:
        r1.release();
        goto L_0x0012;
    L_0x02d7:
        r2 = new java.lang.UnsupportedOperationException;	 Catch:{ all -> 0x02a0 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x02a0 }
        r3.<init>();	 Catch:{ all -> 0x02a0 }
        r4 = "Cannot decode web socket frame with opcode: ";	 Catch:{ all -> 0x02a0 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x02a0 }
        r4 = r11.frameOpcode;	 Catch:{ all -> 0x02a0 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x02a0 }
        r3 = r3.toString();	 Catch:{ all -> 0x02a0 }
        r2.<init>(r3);	 Catch:{ all -> 0x02a0 }
        throw r2;	 Catch:{ all -> 0x02a0 }
    L_0x02f3:
        r2 = r13.isReadable();
        if (r2 == 0) goto L_0x0012;
    L_0x02f9:
        r13.readByte();
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    public WebSocket08FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength) {
        this(expectMaskedFrames, allowExtensions, maxFramePayloadLength, false);
    }

    public WebSocket08FrameDecoder(boolean expectMaskedFrames, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
        this.state = State.READING_FIRST;
        this.expectMaskedFrames = expectMaskedFrames;
        this.allowMaskMismatch = allowMaskMismatch;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadLength = (long) maxFramePayloadLength;
    }

    private void unmask(ByteBuf frame) {
        int i = frame.readerIndex();
        int end = frame.writerIndex();
        int intMask = ((((this.maskingKey[0] & 255) << 24) | ((this.maskingKey[1] & 255) << 16)) | ((this.maskingKey[2] & 255) << 8)) | (this.maskingKey[3] & 255);
        if (frame.order() == ByteOrder.LITTLE_ENDIAN) {
            intMask = Integer.reverseBytes(intMask);
        }
        while (i + 3 < end) {
            frame.setInt(i, frame.getInt(i) ^ intMask);
            i += 4;
        }
        while (i < end) {
            frame.setByte(i, frame.getByte(i) ^ this.maskingKey[i % 4]);
            i++;
        }
    }

    private void protocolViolation(ChannelHandlerContext ctx, String reason) {
        protocolViolation(ctx, new CorruptedFrameException(reason));
    }

    private void protocolViolation(ChannelHandlerContext ctx, CorruptedFrameException ex) {
        this.state = State.CORRUPT;
        if (ctx.channel().isActive()) {
            Object obj;
            if (this.receivedClosingHandshake) {
                obj = Unpooled.EMPTY_BUFFER;
            } else {
                obj = new CloseWebSocketFrame(ErrorCode.ERROR_FORCE_SYSWEBVIEW, null);
            }
            ctx.writeAndFlush(obj).addListener(ChannelFutureListener.CLOSE);
        }
        throw ex;
    }

    private static int toFrameLength(long l) {
        if (l <= 2147483647L) {
            return (int) l;
        }
        throw new TooLongFrameException("Length:" + l);
    }

    protected void checkCloseFrameBody(ChannelHandlerContext ctx, ByteBuf buffer) {
        if (buffer != null && buffer.isReadable()) {
            if (buffer.readableBytes() == 1) {
                protocolViolation(ctx, "Invalid close frame body");
            }
            int idx = buffer.readerIndex();
            buffer.readerIndex(0);
            int statusCode = buffer.readShort();
            if ((statusCode >= 0 && statusCode <= 999) || ((statusCode >= 1004 && statusCode <= ErrorCode.ERROR_COREVERSION_TOOLOW) || (statusCode >= 1012 && statusCode <= 2999))) {
                protocolViolation(ctx, "Invalid close frame getStatus code: " + statusCode);
            }
            if (buffer.isReadable()) {
                try {
                    new Utf8Validator().check(buffer);
                } catch (CorruptedFrameException ex) {
                    protocolViolation(ctx, ex);
                }
            }
            buffer.readerIndex(idx);
        }
    }
}
