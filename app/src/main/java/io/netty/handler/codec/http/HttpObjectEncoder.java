package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.tools.tar.TarConstants;

public abstract class HttpObjectEncoder<H extends HttpMessage> extends MessageToMessageEncoder<Object> {
    static final byte[] CRLF = new byte[]{(byte) 13, (byte) 10};
    private static final ByteBuf CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(CRLF.length).writeBytes(CRLF));
    private static final int ST_CONTENT_CHUNK = 2;
    private static final int ST_CONTENT_NON_CHUNK = 1;
    private static final int ST_INIT = 0;
    private static final byte[] ZERO_CRLF = new byte[]{TarConstants.LF_NORMAL, (byte) 13, (byte) 10};
    private static final byte[] ZERO_CRLF_CRLF = new byte[]{TarConstants.LF_NORMAL, (byte) 13, (byte) 10, (byte) 13, (byte) 10};
    private static final ByteBuf ZERO_CRLF_CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(ZERO_CRLF_CRLF.length).writeBytes(ZERO_CRLF_CRLF));
    private int state = 0;

    protected abstract void encodeInitialLine(ByteBuf byteBuf, H h) throws Exception;

    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        ByteBuf buf = null;
        if (msg instanceof HttpMessage) {
            if (this.state != 0) {
                throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
            }
            int i;
            HttpMessage m = (HttpMessage) msg;
            buf = ctx.alloc().buffer();
            encodeInitialLine(buf, m);
            encodeHeaders(m.headers(), buf);
            buf.writeBytes(CRLF);
            if (HttpUtil.isTransferEncodingChunked(m)) {
                i = 2;
            } else {
                i = 1;
            }
            this.state = i;
        }
        if ((msg instanceof ByteBuf) && !((ByteBuf) msg).isReadable()) {
            out.add(Unpooled.EMPTY_BUFFER);
        } else if ((msg instanceof HttpContent) || (msg instanceof ByteBuf) || (msg instanceof FileRegion)) {
            if (this.state == 0) {
                throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
            }
            long contentLength = contentLength(msg);
            if (this.state == 1) {
                if (contentLength > 0) {
                    if (buf == null || ((long) buf.writableBytes()) < contentLength || !(msg instanceof HttpContent)) {
                        if (buf != null) {
                            out.add(buf);
                        }
                        out.add(encodeAndRetain(msg));
                    } else {
                        buf.writeBytes(((HttpContent) msg).content());
                        out.add(buf);
                    }
                } else if (buf != null) {
                    out.add(buf);
                } else {
                    out.add(Unpooled.EMPTY_BUFFER);
                }
                if (msg instanceof LastHttpContent) {
                    this.state = 0;
                }
            } else if (this.state == 2) {
                if (buf != null) {
                    out.add(buf);
                }
                encodeChunkedContent(ctx, msg, contentLength, out);
            } else {
                throw new Error();
            }
        } else if (buf != null) {
            out.add(buf);
        }
    }

    protected void encodeHeaders(HttpHeaders headers, ByteBuf buf) throws Exception {
        Iterator<Entry<CharSequence, CharSequence>> iter = headers.iteratorCharSequence();
        while (iter.hasNext()) {
            Entry<CharSequence, CharSequence> header = (Entry) iter.next();
            HttpHeadersEncoder.encoderHeader((CharSequence) header.getKey(), (CharSequence) header.getValue(), buf);
        }
    }

    private void encodeChunkedContent(ChannelHandlerContext ctx, Object msg, long contentLength, List<Object> out) {
        if (contentLength > 0) {
            byte[] length = Long.toHexString(contentLength).getBytes(CharsetUtil.US_ASCII);
            ByteBuf buf = ctx.alloc().buffer(length.length + 2);
            buf.writeBytes(length);
            buf.writeBytes(CRLF);
            out.add(buf);
            out.add(encodeAndRetain(msg));
            out.add(CRLF_BUF.duplicate());
        }
        if (msg instanceof LastHttpContent) {
            HttpHeaders headers = ((LastHttpContent) msg).trailingHeaders();
            if (headers.isEmpty()) {
                out.add(ZERO_CRLF_CRLF_BUF.duplicate());
            } else {
                buf = ctx.alloc().buffer();
                buf.writeBytes(ZERO_CRLF);
                try {
                    encodeHeaders(headers, buf);
                } catch (Exception ex) {
                    buf.release();
                    PlatformDependent.throwException(ex);
                }
                buf.writeBytes(CRLF);
                out.add(buf);
            }
            this.state = 0;
        } else if (contentLength == 0) {
            out.add(Unpooled.EMPTY_BUFFER);
        }
    }

    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return (msg instanceof HttpObject) || (msg instanceof ByteBuf) || (msg instanceof FileRegion);
    }

    private static Object encodeAndRetain(Object msg) {
        if (msg instanceof ByteBuf) {
            return ((ByteBuf) msg).retain();
        }
        if (msg instanceof HttpContent) {
            return ((HttpContent) msg).content().retain();
        }
        if (msg instanceof FileRegion) {
            return ((FileRegion) msg).retain();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
    }

    private static long contentLength(Object msg) {
        if (msg instanceof HttpContent) {
            return (long) ((HttpContent) msg).content().readableBytes();
        }
        if (msg instanceof ByteBuf) {
            return (long) ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof FileRegion) {
            return ((FileRegion) msg).count();
        }
        throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(msg));
    }

    @Deprecated
    protected static void encodeAscii(String s, ByteBuf buf) {
        HttpUtil.encodeAscii0(s, buf);
    }
}
