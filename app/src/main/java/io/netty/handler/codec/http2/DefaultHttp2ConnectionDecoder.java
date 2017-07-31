package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection.Endpoint;
import io.netty.handler.codec.http2.Http2Exception.ClosedStreamCreationException;
import io.netty.handler.codec.http2.Http2FrameReader.Configuration;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.List;

public class DefaultHttp2ConnectionDecoder implements Http2ConnectionDecoder {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultHttp2ConnectionDecoder.class);
    private final Http2Connection connection;
    private final Http2ConnectionEncoder encoder;
    private final Http2FrameReader frameReader;
    private Http2FrameListener internalFrameListener;
    private Http2LifecycleManager lifecycleManager;
    private Http2FrameListener listener;
    private final Http2PromisedRequestVerifier requestVerifier;

    private final class FrameReadListener implements Http2FrameListener {
        public int onDataRead(io.netty.channel.ChannelHandlerContext r20, int r21, io.netty.buffer.ByteBuf r22, int r23, boolean r24) throws io.netty.handler.codec.http2.Http2Exception {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Incorrect nodes count for selectOther: B:7:0x0064 in [B:6:0x004b, B:7:0x0064, B:8:0x0065]
	at jadx.core.utils.BlockUtils.selectOther(BlockUtils.java:53)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:64)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r19 = this;
            r0 = r19;
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;
            r4 = r4.connection;
            r0 = r21;
            r17 = r4.stream(r0);
            r0 = r19;
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;
            r15 = r4.flowController();
            r4 = r22.readableBytes();
            r10 = r4 + r23;
            r16 = 1;
            r4 = "DATA";	 Catch:{ all -> 0x0048 }
            r0 = r19;	 Catch:{ all -> 0x0048 }
            r1 = r20;	 Catch:{ all -> 0x0048 }
            r2 = r21;	 Catch:{ all -> 0x0048 }
            r3 = r17;	 Catch:{ all -> 0x0048 }
            r16 = r0.shouldIgnoreHeadersOrDataFrame(r1, r2, r3, r4);	 Catch:{ all -> 0x0048 }
            if (r16 == 0) goto L_0x0065;
        L_0x002f:
            r0 = r17;
            r1 = r22;
            r2 = r23;
            r3 = r24;
            r15.receiveFlowControlledFrame(r0, r1, r2, r3);
            r0 = r17;
            r15.consumeBytes(r0, r10);
            r0 = r19;
            r1 = r21;
            r0.verifyStreamMayHaveExisted(r1);
            r11 = r10;
        L_0x0047:
            return r11;
        L_0x0048:
            r4 = move-exception;
            if (r16 == 0) goto L_0x0064;
        L_0x004b:
            r0 = r17;
            r1 = r22;
            r2 = r23;
            r3 = r24;
            r15.receiveFlowControlledFrame(r0, r1, r2, r3);
            r0 = r17;
            r15.consumeBytes(r0, r10);
            r0 = r19;
            r1 = r21;
            r0.verifyStreamMayHaveExisted(r1);
            r11 = r10;
            goto L_0x0047;
        L_0x0064:
            throw r4;
        L_0x0065:
            r14 = 0;
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State;
            r5 = r17.state();
            r5 = r5.ordinal();
            r4 = r4[r5];
            switch(r4) {
                case 1: goto L_0x0097;
                case 2: goto L_0x0097;
                case 3: goto L_0x00e2;
                case 4: goto L_0x00e2;
                default: goto L_0x0075;
            };
        L_0x0075:
            r4 = r17.id();
            r5 = io.netty.handler.codec.http2.Http2Error.PROTOCOL_ERROR;
            r6 = "Stream %d in unexpected state: %s";
            r7 = 2;
            r7 = new java.lang.Object[r7];
            r8 = 0;
            r9 = r17.id();
            r9 = java.lang.Integer.valueOf(r9);
            r7[r8] = r9;
            r8 = 1;
            r9 = r17.state();
            r7[r8] = r9;
            r14 = io.netty.handler.codec.http2.Http2Exception.streamError(r4, r5, r6, r7);
        L_0x0097:
            r0 = r19;
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;
            r0 = r17;
            r18 = r4.unconsumedBytes(r0);
            r0 = r17;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r1 = r22;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r2 = r23;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r3 = r24;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r15.receiveFlowControlledFrame(r0, r1, r2, r3);	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r0 = r19;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r0 = r17;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r18 = r4.unconsumedBytes(r0);	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            if (r14 == 0) goto L_0x0105;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
        L_0x00b8:
            throw r14;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
        L_0x00b9:
            r13 = move-exception;
            r0 = r19;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r0 = r17;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r4 = r4.unconsumedBytes(r0);	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r12 = r18 - r4;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r10 = r10 - r12;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            throw r13;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
        L_0x00c8:
            r4 = move-exception;
            r0 = r17;
            r15.consumeBytes(r0, r10);
            if (r24 == 0) goto L_0x00e1;
        L_0x00d0:
            r0 = r19;
            r5 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;
            r5 = r5.lifecycleManager;
            r6 = r20.newSucceededFuture();
            r0 = r17;
            r5.closeStreamRemote(r0, r6);
        L_0x00e1:
            throw r4;
        L_0x00e2:
            r4 = r17.id();
            r5 = io.netty.handler.codec.http2.Http2Error.STREAM_CLOSED;
            r6 = "Stream %d in unexpected state: %s";
            r7 = 2;
            r7 = new java.lang.Object[r7];
            r8 = 0;
            r9 = r17.id();
            r9 = java.lang.Integer.valueOf(r9);
            r7[r8] = r9;
            r8 = 1;
            r9 = r17.state();
            r7[r8] = r9;
            r14 = io.netty.handler.codec.http2.Http2Exception.streamError(r4, r5, r6, r7);
            goto L_0x0097;
        L_0x0105:
            r0 = r19;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r4 = r4.listener;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r5 = r20;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r6 = r21;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r7 = r22;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r8 = r23;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r9 = r24;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r10 = r4.onDataRead(r5, r6, r7, r8, r9);	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r0 = r17;
            r15.consumeBytes(r0, r10);
            if (r24 == 0) goto L_0x0133;
        L_0x0122:
            r0 = r19;
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;
            r4 = r4.lifecycleManager;
            r5 = r20.newSucceededFuture();
            r0 = r17;
            r4.closeStreamRemote(r0, r5);
        L_0x0133:
            r11 = r10;
            goto L_0x0047;
        L_0x0136:
            r13 = move-exception;
            r0 = r19;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r4 = io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.this;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r0 = r17;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r4 = r4.unconsumedBytes(r0);	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r12 = r18 - r4;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            r10 = r10 - r12;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            throw r13;	 Catch:{ Http2Exception -> 0x00b9, RuntimeException -> 0x0136, all -> 0x00c8 }
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder.FrameReadListener.onDataRead(io.netty.channel.ChannelHandlerContext, int, io.netty.buffer.ByteBuf, int, boolean):int");
        }

        private FrameReadListener() {
        }

        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endOfStream) throws Http2Exception {
            onHeadersRead(ctx, streamId, headers, 0, (short) 16, false, padding, endOfStream);
        }

        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
            boolean allowHalfClosedRemote = false;
            if (stream == null && !DefaultHttp2ConnectionDecoder.this.connection.streamMayHaveExisted(streamId)) {
                stream = DefaultHttp2ConnectionDecoder.this.connection.remote().createStream(streamId, endOfStream);
                allowHalfClosedRemote = stream.state() == State.HALF_CLOSED_REMOTE;
            }
            if (!shouldIgnoreHeadersOrDataFrame(ctx, streamId, stream, "HEADERS")) {
                switch (stream.state()) {
                    case OPEN:
                    case HALF_CLOSED_LOCAL:
                        break;
                    case HALF_CLOSED_REMOTE:
                        if (!allowHalfClosedRemote) {
                            throw Http2Exception.streamError(stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", Integer.valueOf(stream.id()), stream.state());
                        }
                        break;
                    case CLOSED:
                        throw Http2Exception.streamError(stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", Integer.valueOf(stream.id()), stream.state());
                    case RESERVED_REMOTE:
                        stream.open(endOfStream);
                        break;
                    default:
                        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state: %s", Integer.valueOf(stream.id()), stream.state());
                }
                try {
                    stream.setPriority(streamDependency, weight, exclusive);
                } catch (ClosedStreamCreationException e) {
                }
                DefaultHttp2ConnectionDecoder.this.listener.onHeadersRead(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream);
                if (endOfStream) {
                    DefaultHttp2ConnectionDecoder.this.lifecycleManager.closeStreamRemote(stream, ctx.newSucceededFuture());
                }
            }
        }

        public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
            if (stream == null) {
                try {
                    if (DefaultHttp2ConnectionDecoder.this.connection.streamMayHaveExisted(streamId)) {
                        DefaultHttp2ConnectionDecoder.logger.info("{} ignoring PRIORITY frame for stream {}. Stream doesn't exist but may  have existed", ctx.channel(), Integer.valueOf(streamId));
                        return;
                    }
                    stream = DefaultHttp2ConnectionDecoder.this.connection.remote().createIdleStream(streamId);
                } catch (ClosedStreamCreationException e) {
                }
            } else if (streamCreatedAfterGoAwaySent(streamId)) {
                DefaultHttp2ConnectionDecoder.logger.info("{} ignoring PRIORITY frame for stream {}. Stream created after GOAWAY sent. Last known stream by peer {}", ctx.channel(), Integer.valueOf(streamId), Integer.valueOf(DefaultHttp2ConnectionDecoder.this.connection.remote().lastStreamKnownByPeer()));
                return;
            }
            stream.setPriority(streamDependency, weight, exclusive);
            DefaultHttp2ConnectionDecoder.this.listener.onPriorityRead(ctx, streamId, streamDependency, weight, exclusive);
        }

        public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
            if (stream == null) {
                verifyStreamMayHaveExisted(streamId);
                return;
            }
            switch (stream.state()) {
                case CLOSED:
                    return;
                case IDLE:
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "RST_STREAM received for IDLE stream %d", Integer.valueOf(streamId));
                default:
                    DefaultHttp2ConnectionDecoder.this.listener.onRstStreamRead(ctx, streamId, errorCode);
                    DefaultHttp2ConnectionDecoder.this.lifecycleManager.closeStream(stream, ctx.newSucceededFuture());
                    return;
            }
        }

        public void onSettingsAckRead(ChannelHandlerContext ctx) throws Http2Exception {
            Http2Settings settings = DefaultHttp2ConnectionDecoder.this.encoder.pollSentSettings();
            if (settings != null) {
                applyLocalSettings(settings);
            }
            DefaultHttp2ConnectionDecoder.this.listener.onSettingsAckRead(ctx);
        }

        private void applyLocalSettings(Http2Settings settings) throws Http2Exception {
            Boolean pushEnabled = settings.pushEnabled();
            Configuration config = DefaultHttp2ConnectionDecoder.this.frameReader.configuration();
            Http2HeaderTable headerTable = config.headerTable();
            Http2FrameSizePolicy frameSizePolicy = config.frameSizePolicy();
            if (pushEnabled != null) {
                if (DefaultHttp2ConnectionDecoder.this.connection.isServer()) {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified", new Object[0]);
                }
                DefaultHttp2ConnectionDecoder.this.connection.local().allowPushTo(pushEnabled.booleanValue());
            }
            Long maxConcurrentStreams = settings.maxConcurrentStreams();
            if (maxConcurrentStreams != null) {
                int value = (int) Math.min(maxConcurrentStreams.longValue(), 2147483647L);
                DefaultHttp2ConnectionDecoder.this.connection.remote().maxStreams(value, calculateMaxStreams(value));
            }
            Long headerTableSize = settings.headerTableSize();
            if (headerTableSize != null) {
                headerTable.maxHeaderTableSize((int) Math.min(headerTableSize.longValue(), 2147483647L));
            }
            Integer maxHeaderListSize = settings.maxHeaderListSize();
            if (maxHeaderListSize != null) {
                headerTable.maxHeaderListSize(maxHeaderListSize.intValue());
            }
            Integer maxFrameSize = settings.maxFrameSize();
            if (maxFrameSize != null) {
                frameSizePolicy.maxFrameSize(maxFrameSize.intValue());
            }
            Integer initialWindowSize = settings.initialWindowSize();
            if (initialWindowSize != null) {
                DefaultHttp2ConnectionDecoder.this.flowController().initialWindowSize(initialWindowSize.intValue());
            }
        }

        protected int calculateMaxStreams(int maxConcurrentStreams) {
            int maxStreams = maxConcurrentStreams + 100;
            return maxStreams < 0 ? Integer.MAX_VALUE : maxStreams;
        }

        public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.encoder.remoteSettings(settings);
            DefaultHttp2ConnectionDecoder.this.encoder.writeSettingsAck(ctx, ctx.newPromise());
            DefaultHttp2ConnectionDecoder.this.listener.onSettingsRead(ctx, settings);
        }

        public void onPingRead(ChannelHandlerContext ctx, ByteBuf data) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.encoder.writePing(ctx, true, data.retainedSlice(), ctx.newPromise());
            DefaultHttp2ConnectionDecoder.this.listener.onPingRead(ctx, data);
        }

        public void onPingAckRead(ChannelHandlerContext ctx, ByteBuf data) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.listener.onPingAckRead(ctx, data);
        }

        public void onPushPromiseRead(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) throws Http2Exception {
            Http2Stream parentStream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
            if (!shouldIgnoreHeadersOrDataFrame(ctx, streamId, parentStream, "PUSH_PROMISE")) {
                if (parentStream == null) {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", Integer.valueOf(streamId));
                }
                switch (parentStream.state()) {
                    case OPEN:
                    case HALF_CLOSED_LOCAL:
                        if (!DefaultHttp2ConnectionDecoder.this.requestVerifier.isAuthoritative(ctx, headers)) {
                            throw Http2Exception.streamError(promisedStreamId, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not authoritative", Integer.valueOf(streamId), Integer.valueOf(promisedStreamId));
                        } else if (!DefaultHttp2ConnectionDecoder.this.requestVerifier.isCacheable(headers)) {
                            throw Http2Exception.streamError(promisedStreamId, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not known to be cacheable", Integer.valueOf(streamId), Integer.valueOf(promisedStreamId));
                        } else if (DefaultHttp2ConnectionDecoder.this.requestVerifier.isSafe(headers)) {
                            DefaultHttp2ConnectionDecoder.this.connection.remote().reservePushStream(promisedStreamId, parentStream);
                            DefaultHttp2ConnectionDecoder.this.listener.onPushPromiseRead(ctx, streamId, promisedStreamId, headers, padding);
                            return;
                        } else {
                            throw Http2Exception.streamError(promisedStreamId, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not known to be safe", Integer.valueOf(streamId), Integer.valueOf(promisedStreamId));
                        }
                    default:
                        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state for receiving push promise: %s", Integer.valueOf(parentStream.id()), parentStream.state());
                }
            }
        }

        public void onGoAwayRead(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.onGoAwayRead0(ctx, lastStreamId, errorCode, debugData);
        }

        public void onWindowUpdateRead(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.connection.stream(streamId);
            if (stream == null || stream.state() == State.CLOSED || streamCreatedAfterGoAwaySent(streamId)) {
                verifyStreamMayHaveExisted(streamId);
                return;
            }
            DefaultHttp2ConnectionDecoder.this.encoder.flowController().incrementWindowSize(stream, windowSizeIncrement);
            DefaultHttp2ConnectionDecoder.this.listener.onWindowUpdateRead(ctx, streamId, windowSizeIncrement);
        }

        public void onUnknownFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.onUnknownFrame0(ctx, frameType, streamId, flags, payload);
        }

        private boolean shouldIgnoreHeadersOrDataFrame(ChannelHandlerContext ctx, int streamId, Http2Stream stream, String frameName) throws Http2Exception {
            if (stream == null) {
                if (streamCreatedAfterGoAwaySent(streamId)) {
                    DefaultHttp2ConnectionDecoder.logger.info("{} ignoring {} frame for stream {}. Stream sent after GOAWAY sent", ctx.channel(), frameName, Integer.valueOf(streamId));
                    return true;
                }
                throw Http2Exception.streamError(streamId, Http2Error.STREAM_CLOSED, "Received %s frame for an unknown stream %d", frameName, Integer.valueOf(streamId));
            } else if (!stream.isResetSent() && !streamCreatedAfterGoAwaySent(streamId)) {
                return false;
            } else {
                if (DefaultHttp2ConnectionDecoder.logger.isInfoEnabled()) {
                    InternalLogger access$500 = DefaultHttp2ConnectionDecoder.logger;
                    String str = "{} ignoring {} frame for stream {} {}";
                    Object[] objArr = new Object[3];
                    objArr[0] = ctx.channel();
                    objArr[1] = frameName;
                    objArr[2] = stream.isResetSent() ? "RST_STREAM sent." : "Stream created after GOAWAY sent. Last known stream by peer " + DefaultHttp2ConnectionDecoder.this.connection.remote().lastStreamKnownByPeer();
                    access$500.info(str, objArr);
                }
                return true;
            }
        }

        private boolean streamCreatedAfterGoAwaySent(int streamId) {
            Endpoint<?> remote = DefaultHttp2ConnectionDecoder.this.connection.remote();
            return DefaultHttp2ConnectionDecoder.this.connection.goAwaySent() && remote.isValidStreamId(streamId) && streamId > remote.lastStreamKnownByPeer();
        }

        private void verifyStreamMayHaveExisted(int streamId) throws Http2Exception {
            if (!DefaultHttp2ConnectionDecoder.this.connection.streamMayHaveExisted(streamId)) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", Integer.valueOf(streamId));
            }
        }
    }

    private final class PrefaceFrameListener implements Http2FrameListener {
        private PrefaceFrameListener() {
        }

        private void verifyPrefaceReceived() throws Http2Exception {
            if (!DefaultHttp2ConnectionDecoder.this.prefaceReceived()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received non-SETTINGS as first frame.", new Object[0]);
            }
        }

        public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
            verifyPrefaceReceived();
            return DefaultHttp2ConnectionDecoder.this.internalFrameListener.onDataRead(ctx, streamId, data, padding, endOfStream);
        }

        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endOfStream) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onHeadersRead(ctx, streamId, headers, padding, endOfStream);
        }

        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endOfStream) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onHeadersRead(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endOfStream);
        }

        public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPriorityRead(ctx, streamId, streamDependency, weight, exclusive);
        }

        public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onRstStreamRead(ctx, streamId, errorCode);
        }

        public void onSettingsAckRead(ChannelHandlerContext ctx) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onSettingsAckRead(ctx);
        }

        public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) throws Http2Exception {
            if (!DefaultHttp2ConnectionDecoder.this.prefaceReceived()) {
                DefaultHttp2ConnectionDecoder.this.internalFrameListener = new FrameReadListener();
            }
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onSettingsRead(ctx, settings);
        }

        public void onPingRead(ChannelHandlerContext ctx, ByteBuf data) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPingRead(ctx, data);
        }

        public void onPingAckRead(ChannelHandlerContext ctx, ByteBuf data) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPingAckRead(ctx, data);
        }

        public void onPushPromiseRead(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onPushPromiseRead(ctx, streamId, promisedStreamId, headers, padding);
        }

        public void onGoAwayRead(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.onGoAwayRead0(ctx, lastStreamId, errorCode, debugData);
        }

        public void onWindowUpdateRead(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) throws Http2Exception {
            verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.this.internalFrameListener.onWindowUpdateRead(ctx, streamId, windowSizeIncrement);
        }

        public void onUnknownFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.onUnknownFrame0(ctx, frameType, streamId, flags, payload);
        }
    }

    public DefaultHttp2ConnectionDecoder(Http2Connection connection, Http2ConnectionEncoder encoder, Http2FrameReader frameReader) {
        this(connection, encoder, frameReader, Http2PromisedRequestVerifier.ALWAYS_VERIFY);
    }

    public DefaultHttp2ConnectionDecoder(Http2Connection connection, Http2ConnectionEncoder encoder, Http2FrameReader frameReader, Http2PromisedRequestVerifier requestVerifier) {
        this.internalFrameListener = new PrefaceFrameListener();
        this.connection = (Http2Connection) ObjectUtil.checkNotNull(connection, "connection");
        this.frameReader = (Http2FrameReader) ObjectUtil.checkNotNull(frameReader, "frameReader");
        this.encoder = (Http2ConnectionEncoder) ObjectUtil.checkNotNull(encoder, "encoder");
        this.requestVerifier = (Http2PromisedRequestVerifier) ObjectUtil.checkNotNull(requestVerifier, "requestVerifier");
        if (connection.local().flowController() == null) {
            connection.local().flowController(new DefaultHttp2LocalFlowController(connection));
        }
        ((Http2LocalFlowController) connection.local().flowController()).frameWriter(encoder.frameWriter());
    }

    public void lifecycleManager(Http2LifecycleManager lifecycleManager) {
        this.lifecycleManager = (Http2LifecycleManager) ObjectUtil.checkNotNull(lifecycleManager, "lifecycleManager");
    }

    public Http2Connection connection() {
        return this.connection;
    }

    public final Http2LocalFlowController flowController() {
        return (Http2LocalFlowController) this.connection.local().flowController();
    }

    public void frameListener(Http2FrameListener listener) {
        this.listener = (Http2FrameListener) ObjectUtil.checkNotNull(listener, "listener");
    }

    public Http2FrameListener frameListener() {
        return this.listener;
    }

    Http2FrameListener internalFrameListener() {
        return this.internalFrameListener;
    }

    public boolean prefaceReceived() {
        return FrameReadListener.class == this.internalFrameListener.getClass();
    }

    public void decodeFrame(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Http2Exception {
        this.frameReader.readFrame(ctx, in, this.internalFrameListener);
    }

    public Http2Settings localSettings() {
        Http2Settings settings = new Http2Settings();
        Configuration config = this.frameReader.configuration();
        Http2HeaderTable headerTable = config.headerTable();
        Http2FrameSizePolicy frameSizePolicy = config.frameSizePolicy();
        settings.initialWindowSize(flowController().initialWindowSize());
        settings.maxConcurrentStreams((long) this.connection.remote().maxActiveStreams());
        settings.headerTableSize(headerTable.maxHeaderTableSize());
        settings.maxFrameSize(frameSizePolicy.maxFrameSize());
        settings.maxHeaderListSize(headerTable.maxHeaderListSize());
        if (!this.connection.isServer()) {
            settings.pushEnabled(this.connection.local().allowPushTo());
        }
        return settings;
    }

    public void close() {
        this.frameReader.close();
    }

    private int unconsumedBytes(Http2Stream stream) {
        return flowController().unconsumedBytes(stream);
    }

    void onGoAwayRead0(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) throws Http2Exception {
        if (!this.connection.goAwayReceived() || this.connection.local().lastStreamKnownByPeer() >= lastStreamId) {
            this.listener.onGoAwayRead(ctx, lastStreamId, errorCode, debugData);
            this.connection.goAwayReceived(lastStreamId, errorCode, debugData);
            return;
        }
        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "lastStreamId MUST NOT increase. Current value: %d new value: %d", Integer.valueOf(this.connection.local().lastStreamKnownByPeer()), Integer.valueOf(lastStreamId));
    }

    void onUnknownFrame0(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) throws Http2Exception {
        this.listener.onUnknownFrame(ctx, frameType, streamId, flags, payload);
    }
}
