package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.spdy.SpdyHeaders.HttpNames;
import io.netty.handler.codec.spdy.SpdyHttpHeaders.Names;
import io.netty.util.ReferenceCountUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SpdyHttpDecoder extends MessageToMessageDecoder<SpdyFrame> {
    private final int maxContentLength;
    private final Map<Integer, FullHttpMessage> messageMap;
    private final int spdyVersion;
    private final boolean validateHeaders;

    public SpdyHttpDecoder(SpdyVersion version, int maxContentLength) {
        this(version, maxContentLength, new HashMap(), true);
    }

    public SpdyHttpDecoder(SpdyVersion version, int maxContentLength, boolean validateHeaders) {
        this(version, maxContentLength, new HashMap(), validateHeaders);
    }

    protected SpdyHttpDecoder(SpdyVersion version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap) {
        this(version, maxContentLength, messageMap, true);
    }

    protected SpdyHttpDecoder(SpdyVersion version, int maxContentLength, Map<Integer, FullHttpMessage> messageMap, boolean validateHeaders) {
        if (version == null) {
            throw new NullPointerException("version");
        } else if (maxContentLength <= 0) {
            throw new IllegalArgumentException("maxContentLength must be a_isRightVersion positive integer: " + maxContentLength);
        } else {
            this.spdyVersion = version.getVersion();
            this.maxContentLength = maxContentLength;
            this.messageMap = messageMap;
            this.validateHeaders = validateHeaders;
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        for (Entry<Integer, FullHttpMessage> entry : this.messageMap.entrySet()) {
            ReferenceCountUtil.safeRelease(entry.getValue());
        }
        this.messageMap.clear();
        super.channelInactive(ctx);
    }

    protected FullHttpMessage putMessage(int streamId, FullHttpMessage message) {
        return (FullHttpMessage) this.messageMap.put(Integer.valueOf(streamId), message);
    }

    protected FullHttpMessage getMessage(int streamId) {
        return (FullHttpMessage) this.messageMap.get(Integer.valueOf(streamId));
    }

    protected FullHttpMessage removeMessage(int streamId) {
        return (FullHttpMessage) this.messageMap.remove(Integer.valueOf(streamId));
    }

    protected void decode(ChannelHandlerContext ctx, SpdyFrame msg, List<Object> out) throws Exception {
        SpdySynReplyFrame defaultSpdySynReplyFrame;
        SpdyHeaders frameHeaders;
        int streamId;
        if (msg instanceof SpdySynStreamFrame) {
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame) msg;
            streamId = spdySynStreamFrame.streamId();
            FullHttpRequest httpRequestWithEntity;
            if (SpdyCodecUtil.isServerId(streamId)) {
                int associatedToStreamId = spdySynStreamFrame.associatedStreamId();
                if (associatedToStreamId == 0) {
                    ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INVALID_STREAM));
                } else if (spdySynStreamFrame.isLast()) {
                    ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR));
                } else if (spdySynStreamFrame.isTruncated()) {
                    ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR));
                } else {
                    try {
                        httpRequestWithEntity = createHttpRequest(spdySynStreamFrame, ctx.alloc());
                        httpRequestWithEntity.headers().setInt(Names.STREAM_ID, streamId);
                        httpRequestWithEntity.headers().setInt(Names.ASSOCIATED_TO_STREAM_ID, associatedToStreamId);
                        httpRequestWithEntity.headers().setInt(Names.PRIORITY, spdySynStreamFrame.priority());
                        out.add(httpRequestWithEntity);
                    } catch (Throwable th) {
                        ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR));
                    }
                }
            } else if (spdySynStreamFrame.isTruncated()) {
                defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
                defaultSpdySynReplyFrame.setLast(true);
                frameHeaders = defaultSpdySynReplyFrame.headers();
                frameHeaders.setInt(HttpNames.STATUS, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.code());
                frameHeaders.setObject(HttpNames.VERSION, HttpVersion.HTTP_1_0);
                ctx.writeAndFlush(defaultSpdySynReplyFrame);
            } else {
                try {
                    httpRequestWithEntity = createHttpRequest(spdySynStreamFrame, ctx.alloc());
                    httpRequestWithEntity.headers().setInt(Names.STREAM_ID, streamId);
                    if (spdySynStreamFrame.isLast()) {
                        out.add(httpRequestWithEntity);
                    } else {
                        putMessage(streamId, httpRequestWithEntity);
                    }
                } catch (Throwable th2) {
                    defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId);
                    defaultSpdySynReplyFrame.setLast(true);
                    frameHeaders = defaultSpdySynReplyFrame.headers();
                    frameHeaders.setInt(HttpNames.STATUS, HttpResponseStatus.BAD_REQUEST.code());
                    frameHeaders.setObject(HttpNames.VERSION, HttpVersion.HTTP_1_0);
                    ctx.writeAndFlush(defaultSpdySynReplyFrame);
                }
            }
        } else if (msg instanceof SpdySynReplyFrame) {
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame) msg;
            streamId = spdySynReplyFrame.streamId();
            if (spdySynReplyFrame.isTruncated()) {
                ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR));
                return;
            }
            try {
                FullHttpResponse httpResponseWithEntity = createHttpResponse(spdySynReplyFrame, ctx.alloc(), this.validateHeaders);
                httpResponseWithEntity.headers().setInt(Names.STREAM_ID, streamId);
                if (spdySynReplyFrame.isLast()) {
                    HttpUtil.setContentLength(httpResponseWithEntity, 0);
                    out.add(httpResponseWithEntity);
                    return;
                }
                putMessage(streamId, httpResponseWithEntity);
            } catch (Throwable th3) {
                ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR));
            }
        } else if (msg instanceof SpdyHeadersFrame) {
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame) msg;
            streamId = spdyHeadersFrame.streamId();
            fullHttpMessage = getMessage(streamId);
            if (fullHttpMessage != null) {
                if (!spdyHeadersFrame.isTruncated()) {
                    for (Entry<CharSequence, CharSequence> e : spdyHeadersFrame.headers()) {
                        fullHttpMessage.headers().add((CharSequence) e.getKey(), e.getValue());
                    }
                }
                if (spdyHeadersFrame.isLast()) {
                    HttpUtil.setContentLength(fullHttpMessage, (long) fullHttpMessage.content().readableBytes());
                    removeMessage(streamId);
                    out.add(fullHttpMessage);
                }
            } else if (!SpdyCodecUtil.isServerId(streamId)) {
            } else {
                if (spdyHeadersFrame.isTruncated()) {
                    ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.INTERNAL_ERROR));
                    return;
                }
                try {
                    fullHttpMessage = createHttpResponse(spdyHeadersFrame, ctx.alloc(), this.validateHeaders);
                    fullHttpMessage.headers().setInt(Names.STREAM_ID, streamId);
                    if (spdyHeadersFrame.isLast()) {
                        HttpUtil.setContentLength(fullHttpMessage, 0);
                        out.add(fullHttpMessage);
                        return;
                    }
                    putMessage(streamId, fullHttpMessage);
                } catch (Throwable th4) {
                    ctx.writeAndFlush(new DefaultSpdyRstStreamFrame(streamId, SpdyStreamStatus.PROTOCOL_ERROR));
                }
            }
        } else if (msg instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame) msg;
            streamId = spdyDataFrame.streamId();
            fullHttpMessage = getMessage(streamId);
            if (fullHttpMessage != null) {
                ByteBuf content = fullHttpMessage.content();
                if (content.readableBytes() > this.maxContentLength - spdyDataFrame.content().readableBytes()) {
                    removeMessage(streamId);
                    throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
                }
                ByteBuf spdyDataFrameData = spdyDataFrame.content();
                content.writeBytes(spdyDataFrameData, spdyDataFrameData.readerIndex(), spdyDataFrameData.readableBytes());
                if (spdyDataFrame.isLast()) {
                    HttpUtil.setContentLength(fullHttpMessage, (long) content.readableBytes());
                    removeMessage(streamId);
                    out.add(fullHttpMessage);
                }
            }
        } else if (msg instanceof SpdyRstStreamFrame) {
            removeMessage(((SpdyRstStreamFrame) msg).streamId());
        }
    }

    private static FullHttpRequest createHttpRequest(SpdyHeadersFrame requestFrame, ByteBufAllocator alloc) throws Exception {
        SpdyHeaders headers = requestFrame.headers();
        HttpMethod method = HttpMethod.valueOf(headers.getAsString(HttpNames.METHOD));
        String url = headers.getAsString(HttpNames.PATH);
        HttpVersion httpVersion = HttpVersion.valueOf(headers.getAsString(HttpNames.VERSION));
        headers.remove(HttpNames.METHOD);
        headers.remove(HttpNames.PATH);
        headers.remove(HttpNames.VERSION);
        boolean release = true;
        ByteBuf buffer = alloc.buffer();
        try {
            FullHttpRequest req = new DefaultFullHttpRequest(httpVersion, method, url, buffer);
            headers.remove(HttpNames.SCHEME);
            CharSequence host = (CharSequence) headers.get(HttpNames.HOST);
            headers.remove(HttpNames.HOST);
            req.headers().set(HttpHeaderNames.HOST, host);
            for (Entry<CharSequence, CharSequence> e : requestFrame.headers()) {
                req.headers().add((CharSequence) e.getKey(), e.getValue());
            }
            HttpUtil.setKeepAlive(req, true);
            req.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            release = false;
            return req;
        } finally {
            if (release) {
                buffer.release();
            }
        }
    }

    private static FullHttpResponse createHttpResponse(SpdyHeadersFrame responseFrame, ByteBufAllocator alloc, boolean validateHeaders) throws Exception {
        SpdyHeaders headers = responseFrame.headers();
        HttpResponseStatus status = HttpResponseStatus.parseLine((CharSequence) headers.get(HttpNames.STATUS));
        HttpVersion version = HttpVersion.valueOf(headers.getAsString(HttpNames.VERSION));
        headers.remove(HttpNames.STATUS);
        headers.remove(HttpNames.VERSION);
        boolean release = true;
        ByteBuf buffer = alloc.buffer();
        try {
            FullHttpResponse res = new DefaultFullHttpResponse(version, status, buffer, validateHeaders);
            for (Entry<CharSequence, CharSequence> e : responseFrame.headers()) {
                res.headers().add((CharSequence) e.getKey(), e.getValue());
            }
            HttpUtil.setKeepAlive(res, true);
            res.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            res.headers().remove(HttpHeaderNames.TRAILER);
            release = false;
            return res;
        } finally {
            if (release) {
                buffer.release();
            }
        }
    }
}
