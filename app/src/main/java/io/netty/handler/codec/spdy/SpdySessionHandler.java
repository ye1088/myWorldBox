package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.spdy.SpdySession.PendingWrite;
import io.netty.util.internal.ThrowableUtil;
import java.util.concurrent.atomic.AtomicInteger;

public class SpdySessionHandler extends ChannelDuplexHandler {
    private static final int DEFAULT_MAX_CONCURRENT_STREAMS = Integer.MAX_VALUE;
    private static final int DEFAULT_WINDOW_SIZE = 65536;
    private static final SpdyProtocolException PROTOCOL_EXCEPTION = ((SpdyProtocolException) ThrowableUtil.unknownStackTrace(new SpdyProtocolException(), SpdySessionHandler.class, "handleOutboundMessage(...)"));
    private static final SpdyProtocolException STREAM_CLOSED = ((SpdyProtocolException) ThrowableUtil.unknownStackTrace(new SpdyProtocolException("Stream closed"), SpdySessionHandler.class, "removeStream(...)"));
    private ChannelFutureListener closeSessionFutureListener;
    private int initialReceiveWindowSize = 65536;
    private int initialSendWindowSize = 65536;
    private volatile int initialSessionReceiveWindowSize = 65536;
    private int lastGoodStreamId;
    private int localConcurrentStreams = Integer.MAX_VALUE;
    private final int minorVersion;
    private final AtomicInteger pings = new AtomicInteger();
    private boolean receivedGoAwayFrame;
    private int remoteConcurrentStreams = Integer.MAX_VALUE;
    private boolean sentGoAwayFrame;
    private final boolean server;
    private final SpdySession spdySession = new SpdySession(this.initialSendWindowSize, this.initialReceiveWindowSize);

    private static final class ClosingChannelFutureListener implements ChannelFutureListener {
        private final ChannelHandlerContext ctx;
        private final ChannelPromise promise;

        ClosingChannelFutureListener(ChannelHandlerContext ctx, ChannelPromise promise) {
            this.ctx = ctx;
            this.promise = promise;
        }

        public void operationComplete(ChannelFuture sentGoAwayFuture) throws Exception {
            this.ctx.close(this.promise);
        }
    }

    public SpdySessionHandler(SpdyVersion version, boolean server) {
        if (version == null) {
            throw new NullPointerException("version");
        }
        this.server = server;
        this.minorVersion = version.getMinorVersion();
    }

    public void setSessionReceiveWindowSize(int sessionReceiveWindowSize) {
        if (sessionReceiveWindowSize < 0) {
            throw new IllegalArgumentException("sessionReceiveWindowSize");
        }
        this.initialSessionReceiveWindowSize = sessionReceiveWindowSize;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int streamId;
        int deltaWindowSize;
        if (msg instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame) msg;
            streamId = spdyDataFrame.streamId();
            deltaWindowSize = spdyDataFrame.content().readableBytes() * -1;
            int newSessionWindowSize = this.spdySession.updateReceiveWindowSize(0, deltaWindowSize);
            if (newSessionWindowSize < 0) {
                issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
            if (newSessionWindowSize <= this.initialSessionReceiveWindowSize / 2) {
                int sessionDeltaWindowSize = this.initialSessionReceiveWindowSize - newSessionWindowSize;
                this.spdySession.updateReceiveWindowSize(0, sessionDeltaWindowSize);
                ctx.writeAndFlush(new DefaultSpdyWindowUpdateFrame(0, sessionDeltaWindowSize));
            }
            if (!this.spdySession.isActiveStream(streamId)) {
                spdyDataFrame.release();
                if (streamId <= this.lastGoodStreamId) {
                    issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                    return;
                } else if (!this.sentGoAwayFrame) {
                    issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
                    return;
                } else {
                    return;
                }
            } else if (this.spdySession.isRemoteSideClosed(streamId)) {
                spdyDataFrame.release();
                issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
                return;
            } else if (isRemoteInitiatedId(streamId) || this.spdySession.hasReceivedReply(streamId)) {
                int newWindowSize = this.spdySession.updateReceiveWindowSize(streamId, deltaWindowSize);
                if (newWindowSize < this.spdySession.getReceiveWindowSizeLowerBound(streamId)) {
                    spdyDataFrame.release();
                    issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                    return;
                }
                if (newWindowSize < 0) {
                    while (spdyDataFrame.content().readableBytes() > this.initialReceiveWindowSize) {
                        ctx.writeAndFlush(new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readRetainedSlice(this.initialReceiveWindowSize)));
                    }
                }
                if (newWindowSize <= this.initialReceiveWindowSize / 2 && !spdyDataFrame.isLast()) {
                    int streamDeltaWindowSize = this.initialReceiveWindowSize - newWindowSize;
                    this.spdySession.updateReceiveWindowSize(streamId, streamDeltaWindowSize);
                    ctx.writeAndFlush(new DefaultSpdyWindowUpdateFrame(streamId, streamDeltaWindowSize));
                }
                if (spdyDataFrame.isLast()) {
                    halfCloseStream(streamId, true, ctx.newSucceededFuture());
                }
            } else {
                spdyDataFrame.release();
                issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
        } else if (msg instanceof SpdySynStreamFrame) {
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame) msg;
            streamId = spdySynStreamFrame.streamId();
            if (spdySynStreamFrame.isInvalid() || !isRemoteInitiatedId(streamId) || this.spdySession.isActiveStream(streamId)) {
                issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            }
            if (streamId <= this.lastGoodStreamId) {
                issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
            if (!acceptStream(streamId, spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional())) {
                issueStreamError(ctx, streamId, SpdyStreamStatus.REFUSED_STREAM);
                return;
            }
        } else if (msg instanceof SpdySynReplyFrame) {
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame) msg;
            streamId = spdySynReplyFrame.streamId();
            if (spdySynReplyFrame.isInvalid() || isRemoteInitiatedId(streamId) || this.spdySession.isRemoteSideClosed(streamId)) {
                issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
                return;
            } else if (this.spdySession.hasReceivedReply(streamId)) {
                issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_IN_USE);
                return;
            } else {
                this.spdySession.receivedReply(streamId);
                if (spdySynReplyFrame.isLast()) {
                    halfCloseStream(streamId, true, ctx.newSucceededFuture());
                }
            }
        } else if (msg instanceof SpdyRstStreamFrame) {
            removeStream(((SpdyRstStreamFrame) msg).streamId(), ctx.newSucceededFuture());
        } else if (msg instanceof SpdySettingsFrame) {
            SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame) msg;
            int settingsMinorVersion = spdySettingsFrame.getValue(0);
            if (settingsMinorVersion < 0 || settingsMinorVersion == this.minorVersion) {
                int newConcurrentStreams = spdySettingsFrame.getValue(4);
                if (newConcurrentStreams >= 0) {
                    this.remoteConcurrentStreams = newConcurrentStreams;
                }
                if (spdySettingsFrame.isPersisted(7)) {
                    spdySettingsFrame.removeValue(7);
                }
                spdySettingsFrame.setPersistValue(7, false);
                int newInitialWindowSize = spdySettingsFrame.getValue(7);
                if (newInitialWindowSize >= 0) {
                    updateInitialSendWindowSize(newInitialWindowSize);
                }
            } else {
                issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            }
        } else if (msg instanceof SpdyPingFrame) {
            SpdyPingFrame spdyPingFrame = (SpdyPingFrame) msg;
            if (isRemoteInitiatedId(spdyPingFrame.id())) {
                ctx.writeAndFlush(spdyPingFrame);
                return;
            } else if (this.pings.get() != 0) {
                this.pings.getAndDecrement();
            } else {
                return;
            }
        } else if (msg instanceof SpdyGoAwayFrame) {
            this.receivedGoAwayFrame = true;
        } else if (msg instanceof SpdyHeadersFrame) {
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame) msg;
            streamId = spdyHeadersFrame.streamId();
            if (spdyHeadersFrame.isInvalid()) {
                issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
                return;
            } else if (this.spdySession.isRemoteSideClosed(streamId)) {
                issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
                return;
            } else if (spdyHeadersFrame.isLast()) {
                halfCloseStream(streamId, true, ctx.newSucceededFuture());
            }
        } else if (msg instanceof SpdyWindowUpdateFrame) {
            SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame) msg;
            streamId = spdyWindowUpdateFrame.streamId();
            deltaWindowSize = spdyWindowUpdateFrame.deltaWindowSize();
            if (streamId != 0 && this.spdySession.isLocalSideClosed(streamId)) {
                return;
            }
            if (this.spdySession.getSendWindowSize(streamId) <= Integer.MAX_VALUE - deltaWindowSize) {
                updateSendWindowSize(ctx, streamId, deltaWindowSize);
            } else if (streamId == 0) {
                issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
                return;
            } else {
                issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
                return;
            }
        }
        ctx.fireChannelRead(msg);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        for (Integer streamId : this.spdySession.activeStreams().keySet()) {
            removeStream(streamId.intValue(), ctx.newSucceededFuture());
        }
        ctx.fireChannelInactive();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof SpdyProtocolException) {
            issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
        }
        ctx.fireExceptionCaught(cause);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        sendGoAwayFrame(ctx, promise);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if ((msg instanceof SpdyDataFrame) || (msg instanceof SpdySynStreamFrame) || (msg instanceof SpdySynReplyFrame) || (msg instanceof SpdyRstStreamFrame) || (msg instanceof SpdySettingsFrame) || (msg instanceof SpdyPingFrame) || (msg instanceof SpdyGoAwayFrame) || (msg instanceof SpdyHeadersFrame) || (msg instanceof SpdyWindowUpdateFrame)) {
            handleOutboundMessage(ctx, msg, promise);
        } else {
            ctx.write(msg, promise);
        }
    }

    private void handleOutboundMessage(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        int streamId;
        if (msg instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame) msg;
            streamId = spdyDataFrame.streamId();
            if (this.spdySession.isLocalSideClosed(streamId)) {
                spdyDataFrame.release();
                promise.setFailure(PROTOCOL_EXCEPTION);
                return;
            }
            int dataLength = spdyDataFrame.content().readableBytes();
            int sendWindowSize = Math.min(this.spdySession.getSendWindowSize(streamId), this.spdySession.getSendWindowSize(0));
            if (sendWindowSize <= 0) {
                this.spdySession.putPendingWrite(streamId, new PendingWrite(spdyDataFrame, promise));
                return;
            } else if (sendWindowSize < dataLength) {
                this.spdySession.updateSendWindowSize(streamId, sendWindowSize * -1);
                this.spdySession.updateSendWindowSize(0, sendWindowSize * -1);
                SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readRetainedSlice(sendWindowSize));
                this.spdySession.putPendingWrite(streamId, new PendingWrite(spdyDataFrame, promise));
                context = ctx;
                ctx.write(partialDataFrame).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            SpdySessionHandler.this.issueSessionError(context, SpdySessionStatus.INTERNAL_ERROR);
                        }
                    }
                });
                return;
            } else {
                this.spdySession.updateSendWindowSize(streamId, dataLength * -1);
                this.spdySession.updateSendWindowSize(0, dataLength * -1);
                context = ctx;
                promise.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            SpdySessionHandler.this.issueSessionError(context, SpdySessionStatus.INTERNAL_ERROR);
                        }
                    }
                });
                if (spdyDataFrame.isLast()) {
                    halfCloseStream(streamId, false, promise);
                }
            }
        } else if (msg instanceof SpdySynStreamFrame) {
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame) msg;
            streamId = spdySynStreamFrame.streamId();
            if (isRemoteInitiatedId(streamId)) {
                promise.setFailure(PROTOCOL_EXCEPTION);
                return;
            }
            if (!acceptStream(streamId, spdySynStreamFrame.priority(), spdySynStreamFrame.isUnidirectional(), spdySynStreamFrame.isLast())) {
                promise.setFailure(PROTOCOL_EXCEPTION);
                return;
            }
        } else if (msg instanceof SpdySynReplyFrame) {
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame) msg;
            streamId = spdySynReplyFrame.streamId();
            if (!isRemoteInitiatedId(streamId) || this.spdySession.isLocalSideClosed(streamId)) {
                promise.setFailure(PROTOCOL_EXCEPTION);
                return;
            } else if (spdySynReplyFrame.isLast()) {
                halfCloseStream(streamId, false, promise);
            }
        } else if (msg instanceof SpdyRstStreamFrame) {
            removeStream(((SpdyRstStreamFrame) msg).streamId(), promise);
        } else if (msg instanceof SpdySettingsFrame) {
            SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame) msg;
            int settingsMinorVersion = spdySettingsFrame.getValue(0);
            if (settingsMinorVersion < 0 || settingsMinorVersion == this.minorVersion) {
                int newConcurrentStreams = spdySettingsFrame.getValue(4);
                if (newConcurrentStreams >= 0) {
                    this.localConcurrentStreams = newConcurrentStreams;
                }
                if (spdySettingsFrame.isPersisted(7)) {
                    spdySettingsFrame.removeValue(7);
                }
                spdySettingsFrame.setPersistValue(7, false);
                int newInitialWindowSize = spdySettingsFrame.getValue(7);
                if (newInitialWindowSize >= 0) {
                    updateInitialReceiveWindowSize(newInitialWindowSize);
                }
            } else {
                promise.setFailure(PROTOCOL_EXCEPTION);
                return;
            }
        } else if (msg instanceof SpdyPingFrame) {
            SpdyPingFrame spdyPingFrame = (SpdyPingFrame) msg;
            if (isRemoteInitiatedId(spdyPingFrame.id())) {
                ctx.fireExceptionCaught(new IllegalArgumentException("invalid PING ID: " + spdyPingFrame.id()));
                return;
            }
            this.pings.getAndIncrement();
        } else if (msg instanceof SpdyGoAwayFrame) {
            promise.setFailure(PROTOCOL_EXCEPTION);
            return;
        } else if (msg instanceof SpdyHeadersFrame) {
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame) msg;
            streamId = spdyHeadersFrame.streamId();
            if (this.spdySession.isLocalSideClosed(streamId)) {
                promise.setFailure(PROTOCOL_EXCEPTION);
                return;
            } else if (spdyHeadersFrame.isLast()) {
                halfCloseStream(streamId, false, promise);
            }
        } else if (msg instanceof SpdyWindowUpdateFrame) {
            promise.setFailure(PROTOCOL_EXCEPTION);
            return;
        }
        ctx.write(msg, promise);
    }

    private void issueSessionError(ChannelHandlerContext ctx, SpdySessionStatus status) {
        sendGoAwayFrame(ctx, status).addListener(new ClosingChannelFutureListener(ctx, ctx.newPromise()));
    }

    private void issueStreamError(ChannelHandlerContext ctx, int streamId, SpdyStreamStatus status) {
        boolean fireChannelRead = !this.spdySession.isRemoteSideClosed(streamId);
        ChannelPromise promise = ctx.newPromise();
        removeStream(streamId, promise);
        SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, status);
        ctx.writeAndFlush(spdyRstStreamFrame, promise);
        if (fireChannelRead) {
            ctx.fireChannelRead(spdyRstStreamFrame);
        }
    }

    private boolean isRemoteInitiatedId(int id) {
        boolean serverId = SpdyCodecUtil.isServerId(id);
        return (this.server && !serverId) || (!this.server && serverId);
    }

    private void updateInitialSendWindowSize(int newInitialWindowSize) {
        int deltaWindowSize = newInitialWindowSize - this.initialSendWindowSize;
        this.initialSendWindowSize = newInitialWindowSize;
        this.spdySession.updateAllSendWindowSizes(deltaWindowSize);
    }

    private void updateInitialReceiveWindowSize(int newInitialWindowSize) {
        int deltaWindowSize = newInitialWindowSize - this.initialReceiveWindowSize;
        this.initialReceiveWindowSize = newInitialWindowSize;
        this.spdySession.updateAllReceiveWindowSizes(deltaWindowSize);
    }

    private boolean acceptStream(int streamId, byte priority, boolean remoteSideClosed, boolean localSideClosed) {
        if (this.receivedGoAwayFrame || this.sentGoAwayFrame) {
            return false;
        }
        boolean remote = isRemoteInitiatedId(streamId);
        if (this.spdySession.numActiveStreams(remote) >= (remote ? this.localConcurrentStreams : this.remoteConcurrentStreams)) {
            return false;
        }
        this.spdySession.acceptStream(streamId, priority, remoteSideClosed, localSideClosed, this.initialSendWindowSize, this.initialReceiveWindowSize, remote);
        if (remote) {
            this.lastGoodStreamId = streamId;
        }
        return true;
    }

    private void halfCloseStream(int streamId, boolean remote, ChannelFuture future) {
        if (remote) {
            this.spdySession.closeRemoteSide(streamId, isRemoteInitiatedId(streamId));
        } else {
            this.spdySession.closeLocalSide(streamId, isRemoteInitiatedId(streamId));
        }
        if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
            future.addListener(this.closeSessionFutureListener);
        }
    }

    private void removeStream(int streamId, ChannelFuture future) {
        this.spdySession.removeStream(streamId, STREAM_CLOSED, isRemoteInitiatedId(streamId));
        if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
            future.addListener(this.closeSessionFutureListener);
        }
    }

    private void updateSendWindowSize(final ChannelHandlerContext ctx, int streamId, int deltaWindowSize) {
        this.spdySession.updateSendWindowSize(streamId, deltaWindowSize);
        while (true) {
            PendingWrite pendingWrite = this.spdySession.getPendingWrite(streamId);
            if (pendingWrite != null) {
                SpdyDataFrame spdyDataFrame = pendingWrite.spdyDataFrame;
                int dataFrameSize = spdyDataFrame.content().readableBytes();
                int writeStreamId = spdyDataFrame.streamId();
                int sendWindowSize = Math.min(this.spdySession.getSendWindowSize(writeStreamId), this.spdySession.getSendWindowSize(0));
                if (sendWindowSize <= 0) {
                    return;
                }
                if (sendWindowSize < dataFrameSize) {
                    this.spdySession.updateSendWindowSize(writeStreamId, sendWindowSize * -1);
                    this.spdySession.updateSendWindowSize(0, sendWindowSize * -1);
                    ctx.writeAndFlush(new DefaultSpdyDataFrame(writeStreamId, spdyDataFrame.content().readRetainedSlice(sendWindowSize))).addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (!future.isSuccess()) {
                                SpdySessionHandler.this.issueSessionError(ctx, SpdySessionStatus.INTERNAL_ERROR);
                            }
                        }
                    });
                } else {
                    this.spdySession.removePendingWrite(writeStreamId);
                    this.spdySession.updateSendWindowSize(writeStreamId, dataFrameSize * -1);
                    this.spdySession.updateSendWindowSize(0, dataFrameSize * -1);
                    if (spdyDataFrame.isLast()) {
                        halfCloseStream(writeStreamId, false, pendingWrite.promise);
                    }
                    ctx.writeAndFlush(spdyDataFrame, pendingWrite.promise).addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (!future.isSuccess()) {
                                SpdySessionHandler.this.issueSessionError(ctx, SpdySessionStatus.INTERNAL_ERROR);
                            }
                        }
                    });
                }
            } else {
                return;
            }
        }
    }

    private void sendGoAwayFrame(ChannelHandlerContext ctx, ChannelPromise future) {
        if (ctx.channel().isActive()) {
            ChannelFuture f = sendGoAwayFrame(ctx, SpdySessionStatus.OK);
            if (this.spdySession.noActiveStreams()) {
                f.addListener(new ClosingChannelFutureListener(ctx, future));
                return;
            } else {
                this.closeSessionFutureListener = new ClosingChannelFutureListener(ctx, future);
                return;
            }
        }
        ctx.close(future);
    }

    private ChannelFuture sendGoAwayFrame(ChannelHandlerContext ctx, SpdySessionStatus status) {
        if (this.sentGoAwayFrame) {
            return ctx.newSucceededFuture();
        }
        this.sentGoAwayFrame = true;
        return ctx.writeAndFlush(new DefaultSpdyGoAwayFrame(this.lastGoodStreamId, status));
    }
}
